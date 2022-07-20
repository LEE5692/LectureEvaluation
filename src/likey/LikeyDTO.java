package likey;

public class LikeyDTO {

		String UserID;
		int evaluationID;
		String userIP;
		public String getUserID() {
			return UserID;
		}
		public void setUserID(String userID) {
			UserID = userID;
		}
		public int getEvaluationID() {
			return evaluationID;
		}
		public void setEvaluationID(int evaluationID) {
			this.evaluationID = evaluationID;
		}
		public String getUserIP() {
			return userIP;
		}
		public void setUserIP(String userIP) {
			this.userIP = userIP;
		}
		public LikeyDTO() {
			
		}
		public LikeyDTO(String userID, int evaluationID, String userIP) {
			super();
			UserID = userID;
			this.evaluationID = evaluationID;
			this.userIP = userIP;
		}
}
