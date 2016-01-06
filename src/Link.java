public class Link
{
		private String link;
		private String value;
		
		public Link(String link,String value){
				this.link=link;
				this.value=value;
		}
		
		public String getLink(){ return link;}
		public String getValue ( ) { return value;}

		@Override
		public String toString ( ) {
				// TODO: Implement this method
				return super.toString ( )+"\nLink = "+link+"\nValue = "+value;
		}
		
}
