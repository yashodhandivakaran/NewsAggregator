
Parse.Cloud.job("rssFeeds", function(request, status) {

  var newsServiceResult = 'test';
  var NewsServices = Parse.Object.extend("Newspaper");
  var query = new Parse.Query(NewsServices);
  query.find({
    success: function(results) {
      console.log("Successfully retrieved " + results.length + " scores.");
      // Do something with the returned Parse.Object values

      for (var i = 0; i < results.length; i++) {
        var object = results[i];
        var feed = object.get('rss_url');
        console.log("rss_url "+feed);

        (function(object){

          Parse.Cloud.httpRequest({
            url: feed,
            followRedirects: true
          }).then(
              function(httpResponse) {
                //console.log("feeds:  "+httpResponse.text);
                var soapData = httpResponse.text;
                var xmlreader = require('cloud/xmlreader.js');

                xmlreader.read(soapData, function (err, xmldata) {
                  if(err) {
                    response.error("Error " +err);
                    return console.log(err);
                  }


                  console.log("date: ",xmldata.rss.channel.pubdate);

                  //var date = new Date(xmldata.rss.channel.pubdate);

                  xmldata.rss.channel.item.each(function (i, item) {


                    var News = Parse.Object.extend("News");
                    var news = new News();

                    news.set("title", item.title.text());
                    news.set("link", item.link.text());
                    news.set("description", item.description.text());
                    //news.set("published",date);
                    news.set("newspaper_uid",object.get('newspaper_uid'));

                    news.save(null, {
                      success: function(news) {
                        // Execute any logic that should take place after the object is saved.
                      },
                      error: function(news, error) {
                        // Execute any logic that should take place if the save fails.
                        // error is a Parse.Error with an error code and message.
                      }
                    });
                  });
                  return true;
                });

              },function(httpResponse){
                console.error('Request failed with response code ' + httpResponse.status);
              });
        })(object);
      }
    },
    error: function(error) {
      console.log("Error: " + error.code + " " + error.message);
    }
  });

  });
