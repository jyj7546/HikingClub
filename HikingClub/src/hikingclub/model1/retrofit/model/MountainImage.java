package hikingclub.model1.retrofit.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MountainImage {

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

					@SerializedName("imgfilename")
					public String imgFileName;

					@SerializedName("imgname")
					public String imgName;

					@SerializedName("imgno")
					public int imgNo;

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
