package project1;

public class ChatContent {

	
	//친구 리스트에 대한 클래스
	//MainPage(채팅방 페이지)에서 활용

	   private String Id;
	   private String name;
	   private String chatcontent;
	   private String toname;
	   
	   
	   public String getToname() {
	      return toname;
	   }


	   public void setToname(String toname) {
	      this.toname = toname;
	   }


	   public String getId() {
	      return Id;
	   }


	   public String getName() {
	      return name;
	   }


	   public void setName(String name) {
	      this.name = name;
	   }


	   public void setId(String id) {
	      Id = id;
	   }


	   public String getChatcontent() {
	      return chatcontent;
	   }


	   public ChatContent(String Id, String chatcontent, String name, String toname) {
	      this.Id = Id;
	      this.chatcontent = chatcontent;
	      this.name = name;
	      this.toname = toname;
	      
	      
	   }
	   

	

	
}
