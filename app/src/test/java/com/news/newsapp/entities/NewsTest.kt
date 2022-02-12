package com.news.newsapp.entities

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import com.google.gson.Gson




class NewsTest {
    var jsonNews = "{\n" +
            "      \"source\": {\n" +
            "        \"id\": \"reuters\",\n" +
            "        \"name\": \"Reuters\"\n" +
            "      },\n" +
            "      \"author\": null,\n" +
            "      \"title\": \"Russia masses more troops near Ukraine, U.S. says - Reuters\",\n" +
            "      \"description\": \"Russia is massing yet more troops near Ukraine and an invasion could come at any time, perhaps before the end of this month's Winter Olympics, U.S. Secretary of State Antony Blinken said on Friday.\",\n" +
            "      \"url\": \"https://www.reuters.com/world/europe/us-says-russia-masses-more-troops-near-ukraine-invasion-could-come-any-time-2022-02-11/\",\n" +
            "      \"urlToImage\": \"https://www.reuters.com/resizer/fY-cJPLgMiXY8Xhy57mEty4cZio=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/DQZXWVC2ZZJZJCSN543OQFCVVA.jpg\",\n" +
            "      \"publishedAt\": \"2022-02-11T13:55:00Z\",\n" +
            "      \"content\": \"MOSCOW/ADELAIDE, Feb 11 (Reuters) - Russia is massing yet more troops near Ukraine and an invasion could come at any time, perhaps before the end of this month's Winter Olympics, U.S. Secretary of St… [+5491 chars]\"\n" +
            "    }"
    private var news: News = Gson().fromJson(jsonNews, News::class.java)
    @Before
    fun start() {
        news.id = 0;
    }

    @Test
    fun test_default_value() {
        Assert.assertTrue( news.id == 0)
    }

    @Test
    fun test_toString() {
        Assert.assertEquals("News(source=Source(id=reuters, name=Reuters), author=null, title=Russia masses more troops near Ukraine, U.S. says - Reuters, description=Russia is massing yet more troops near Ukraine and an invasion could come at any time, perhaps before the end of this month's Winter Olympics, U.S. Secretary of State Antony Blinken said on Friday., url=https://www.reuters.com/world/europe/us-says-russia-masses-more-troops-near-ukraine-invasion-could-come-any-time-2022-02-11/, urlToImage=https://www.reuters.com/resizer/fY-cJPLgMiXY8Xhy57mEty4cZio=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/DQZXWVC2ZZJZJCSN543OQFCVVA.jpg, publishedAt=2022-02-11T13:55:00Z, content=MOSCOW/ADELAIDE, Feb 11 (Reuters) - Russia is massing yet more troops near Ukraine and an invasion could come at any time, perhaps before the end of this month's Winter Olympics, U.S. Secretary of St… [+5491 chars])", news.toString())
    }
}