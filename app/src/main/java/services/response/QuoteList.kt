package services.response

data class QuoteList(
    val count: Int? = null,
    val lastItemIndex: Int? = null,
    val page: Int? = null,
    val results: List<Result>? = null,
    val totalCount: Int? = null,
    val totalPages: Int? = null
)