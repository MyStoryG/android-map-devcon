package devcon.map.model

import com.google.gson.annotations.SerializedName

/**
 * Kakao Map Search API Response
 * @param meta
 * @param documents
 */
data class KakaoMapSearchResponse(
    val meta: Meta,
    val documents: List<Document>,
)

/**
 * Response Metadata
 * @param totalCount 검색어에 검색된 문서 수
 * @param pageableCount total_count 중 노출 가능 문서 수
 * @param isEnd 현재 페이지가 마지막 페이지인지 여부
 * @param sameName
 */
data class Meta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("same_name")
    val sameName: SameName,
)

/**
 * About regional and keyword analysis of query words
 * @param region 질의어에서 인식된 지역의 리스트
 * @param keyword 질의어에서 지역 정보를 제외한 키워드
 * @param selectedRegion 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보
 */
data class SameName(
    val region: List<String>,
    val keyword: String,
    @SerializedName("selected_region")
    val selectedRegion: String,
)

/**
 * Response Result
 * @param id 장소 ID
 * @param placeName 장소명, 업체명
 * @param categoryName 카테고리 이름
 * @param categoryGroupCode 중요 카테고리만 그룹핑한 카테고리 그룹 코드
 * @param categoryGroupName 중요 카테고리만 그룹핑한 카테고리 그룹명
 * @param phone 전화번호
 * @param addressName 전체 지번 주소
 * @param roadAddressName 전체 도로명 주소
 * @param x X 좌표값, 경위도인 경우 longitude (경도)
 * @param y Y 좌표값, 경위도인 경우 latitude(위도)
 * @param placeUrl 장소 상세페이지 URL
 * @param distance 중심좌표까지의 거리. 단, x,y 파라미터를 준 경우에만 존재. 단위는 meter
 */
data class Document(
    val id: String,
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("category_group_code")
    val categoryGroupCode: String,
    @SerializedName("category_group_name")
    val categoryGroupName: String,
    val phone: String,
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("road_address_name")
    val roadAddressName: String,
    val x: String,
    val y: String,
    @SerializedName("place_url")
    val placeUrl: String,
    val distance: String,
)
