package hikingclub.model1.retrofit.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MountainInfo {

	@SerializedName("response")
	public Response response;

	public class Response {

		@SerializedName("body")
		public Body body;

		public class Body {

			@SerializedName("items")
			public Items items;

			public class Items {

				@SerializedName("item")
				public List<Item> item;

				public class Item {

					@SerializedName("mntiadd")
					public String mntiAdd;

					@SerializedName("mntiname")
					public String mntiName;

					@SerializedName("mntilistno")
					public int mntiListNo;

					@SerializedName("mntiadmin")
					public String mntiAdmin;

					@SerializedName("mntiadminnum")
					public String mntiAdminNum;

					@SerializedName("mntidetails")
					public String mntiDetails;

				}

			}

			@SerializedName("numOfRows")
			public int numOfRows;

			@SerializedName("pageNo")
			public int pageNo;

			@SerializedName("totalCount")
			public int totalCount;

		}
	}
}
