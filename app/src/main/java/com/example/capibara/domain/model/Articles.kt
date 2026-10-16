package com.example.capibara.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class NewsResponse(
    val totalArticles: Int,
    val articles: List<Article>
){
    companion object{
        fun fromJson() : List<Article> {
            val json = "{\n" +
                    "  \"information\" : {\n" +
                    "    \"realTimeArticles\" : {\n" +
                    "      \"message\" : \"Real-time news data is only available on paid plans. Free plan has a 12-hour delay. Upgrade your plan here to remove the delay: https://gnews.io/change-plan\"\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"totalArticles\" : 977,\n" +
                    "  \"articles\" : [ {\n" +
                    "    \"id\" : \"5fefc26e6e3c1c747e65181964f0aa93\",\n" +
                    "    \"title\" : \"Врач раскрыл правду о пользе черники для зрения\",\n" +
                    "    \"description\" : \"Черника не может защитить глаза от заболеваний или компенсировать последствия работы за компьютером, заявил офтальмолог, офтальмохирург АО «Медицина» (клиника академика Ройтберга), кандидат медицинских наук Сино Гозиев. Правду о пользе этой ягоды для органов зрения он раскрыл в разговоре с «Лентой.ру»\",\n" +
                    "    \"content\" : \"Черника не может защитить глаза от заболеваний или компенсировать последствия работы за компьютером, заявил офтальмолог, офтальмохирург АО «Медицина» (клиника академика Ройтберга), кандидат медицинских наук Сино Гозиев. Правду о пользе этой ягоды для... [1207 chars]\",\n" +
                    "    \"url\" : \"https://lenta.ru/news/2026/10/15/vrach-raskryl-pravdu-o-polze-cherniki-dlya-zreniya/\",\n" +
                    "    \"image\" : \"https://icdn.lenta.ru/images/2026/10/15/17/20261015172717979/share_00612bcd65491fda80431106a59ff503.jpg\",\n" +
                    "    \"publishedAt\" : \"2026-10-15T17:27:00Z\",\n" +
                    "    \"lang\" : \"ru\",\n" +
                    "    \"source\" : {\n" +
                    "      \"id\" : \"5bd31a6a1cecbe7abcf12b7671c8b780\",\n" +
                    "      \"name\" : \"Lenta.ru\",\n" +
                    "      \"url\" : \"https://lenta.ru\",\n" +
                    "      \"country\" : \"ru\"\n" +
                    "    }\n" +
                    "  },  {\n" +
                    "    \"id\" : \"9a60ddd22dc114b1c2b63c4fbe8103f9\",\n" +
                    "    \"title\" : \"Врач раскрыл наиболее характерные проявления тромбоза\",\n" +
                    "    \"description\" : \"Тромбоз может проявляться по-разному, рассказал заведующий отделением гематологии АО «Медицина» (клиника академика Ройтберга) Радион Тарновский. Наиболее характерные признаки опасного состояния он раскрыл в разговоре с «Лентой.ру»\",\n" +
                    "    \"content\" : \"Тромбоз может проявляться по-разному, рассказал заведующий отделением гематологии АО «Медицина» (клиника академика Ройтберга) Радион Тарновский. Наиболее характерные признаки опасного состояния он раскрыл в разговоре с «Лентой.ру».\\n«При тромбозе глуб... [745 chars]\",\n" +
                    "    \"url\" : \"https://lenta.ru/news/2026/10/06/simptomy-tromboza/\",\n" +
                    "    \"image\" : \"https://icdn.lenta.ru/images/2026/09/22/21/20260922215637340/share_aa8edab1326ec3099de160f1ea5ced34.jpg\",\n" +
                    "    \"publishedAt\" : \"2026-10-06T11:26:00Z\",\n" +
                    "    \"lang\" : \"ru\",\n" +
                    "    \"source\" : {\n" +
                    "      \"id\" : \"5bd31a6a1cecbe7abcf12b7671c8b780\",\n" +
                    "      \"name\" : \"Lenta.ru\",\n" +
                    "      \"url\" : \"https://lenta.ru\",\n" +
                    "      \"country\" : \"ru\"\n" +
                    "    }\n" +
                    "  } ]\n" +
                    "}"
            val jsonParser = Json { ignoreUnknownKeys = true }
            val news = jsonParser.decodeFromString<NewsResponse>(json)
            return news.articles
        }
    }
}

@Serializable
data class Article(
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val lang: String,
    val source: Source
)

@kotlinx.serialization.Serializable
data class Source(
    val id: String,
    val name: String,
    val url: String,
    val country: String
)
