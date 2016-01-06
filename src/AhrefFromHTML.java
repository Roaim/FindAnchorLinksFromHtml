
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AhrefFromHTML     
{
		private String html;
		private int flag = 1;
		private StringBuilder result;
		private ArrayList<Link> links;
		private String toString;
		
		public AhrefFromHTML(String html,int flag){
				this.html = html;
				this.flag = flag;
				result = new StringBuilder();
		}
		
		public AhrefFromHTML(String html){
				this(html,1);
		}
		
		public AhrefFromHTML(File file,int flag){
				this(getStringFromFile(file),flag);
		}
		
		public AhrefFromHTML(File file){
				this(file,1);
		}
		
		public AhrefFromHTML(URL url,int flag){
				this(getStringFromUrl(url),flag);
		}
		
		public AhrefFromHTML(URL url){
				this(url,1);
		}

		@Override
		public String toString ( ) {
				if(toString==null) {
						links = new ArrayList<>();
						toString = "\nTotal links found = "+extractLinks(html,flag)+result.toString();
				}
				return toString;
		}
		
		public ArrayList<Link> getLinks(){
				if(links==null){
						toString();
				}
				return links;
		}
		
		public static ArrayList<Link> getLinksArray(String html){
				AhrefFromHTML alfh = new AhrefFromHTML(html);
				return alfh.getLinks();
		}

		public static ArrayList<Link> getLinksArray(File file){
				AhrefFromHTML alfh = new AhrefFromHTML(file);
				return alfh.getLinks();
		}

		public static ArrayList<Link> getLinksArray(URL url){
				AhrefFromHTML alfh = new AhrefFromHTML(url);
				return alfh.getLinks();
		}
		
		public static String getLinks(String html){
				AhrefFromHTML alfh = new AhrefFromHTML(html);
				return alfh.toString();
		}
		
		public static String getLinks(File file){
				AhrefFromHTML alfh = new AhrefFromHTML(file);
				return alfh.toString();
		}
		
		public static String getLinks(URL url){
				AhrefFromHTML alfh = new AhrefFromHTML(url);
				return alfh.toString();
		}
		
		public static String getStringFromUrl(URL url){
				InputStream in;
				String str = null;
				try {
						HttpURLConnection con = (HttpURLConnection) url.openConnection ( );
						con.connect();
						in = con.getInputStream();
						str = getStringFromInputStream(in);
						con.disconnect();
				} catch (Exception e) {
						e.printStackTrace();
				}
				return str;
		}
		
		public static String getStringFromFile(File file){
				FileInputStream in ;
				String sff = null;
				try {
						in = new FileInputStream ( file );
						sff = getStringFromInputStream(in);
				} catch (Exception e) {
						e.printStackTrace();
				}
				return sff;
		}
		
		public static String getStringFromInputStream ( InputStream in ) throws IOException {
				// TODO: Implement this method
				StringBuilder s = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line ;
				while((line=br.readLine())!=null){
						s.append(line.trim());
				}
				br.close();
				in.close();
				return s.toString();
		}
		
		private int extractLinks ( String line,int linkNumbering) {
				String regex = "<a";
				if(line!=null&&line.contains("<a") && line.contains("href=\"") && line.contains(">") && line.contains("</a>")){
						int rIndex = line.indexOf(regex);
						String sub = line.substring(rIndex);
						String href = "href=\"";
						int start = sub.indexOf ( href )+href.length();
						int end = sub.indexOf ( '"', start );
						String link = sub.substring(start,end);
						result.append("\n\n");
						result.append("###link("+linkNumbering+")\t: "+link);
						String restWithValue = sub.substring(end);
						int vStart = restWithValue.indexOf('>')+1;
						String regexClose = "</a>";
						int vEnd = restWithValue.indexOf ( regexClose );
						String value = restWithValue.substring(vStart,vEnd);
						result.append("\n");
						result.append("@@Value("+linkNumbering+")\t: "+value);
						links.add(new Link(link,value));
						String rest = restWithValue.substring(vEnd);
						return 1 + extractLinks ( rest,++linkNumbering );
				}
				return 0;
		}
}
