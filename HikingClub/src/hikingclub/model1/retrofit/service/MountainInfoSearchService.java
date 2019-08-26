package hikingclub.model1.retrofit.service;

import hikingclub.model1.helper.RetrofitHelper;
import hikingclub.model1.retrofit.model.MountainImage;
import hikingclub.model1.retrofit.model.MountainInfo;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MountainInfoSearchService {

	public static final String serviceKey = "%2BZzIadq29h3cbyoRknKU414w%2BNX7YiTk5XxHgqyMvwdvNLWPDpjKCBpLDAuol0OH6ZzzpPV4tWkDQc5j6A7WAg%3D%3D"; 
	public static final String json = "json"; 
	
	public static final Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit("http://apis.data.go.kr/1400000/service/cultureInfoService/");

	// 산 정보 목록 검색
	@GET("mntInfoOpenAPI?ServiceKey=" + serviceKey + "&_type=" + json)
	Call<MountainInfo> getMountainInfo(@Query("searchWrd") String searchWrd);
	
	// 산 이미지 정보 검색, + http://www.forest.go.kr/images/data/down/mountain/
	@GET("mntInfoImgOpenAPI?ServiceKey=" + serviceKey + "&_type=" + json)
	Call<MountainImage> getMountainImage(@Query("mntiListNo") int mntiListNo);
	
}
