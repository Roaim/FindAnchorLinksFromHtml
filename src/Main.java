
import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Main
{
		private static final String welcomeMsg = 
		"WelCome to <a href=\"\"></a> extractor:\nPlease type the--\n1. html code or\n2. input a file address or\n3. input a url addresses\nto extract anchor tag address and values.\nPlease type \"x\" to exit.";
		
	public static void main(String[] args)
	{
			for(int i=1;i<10;i++){
					if(i==5)
							System.out.println(formattedWM(welcomeMsg));
					else
							System.out.print("###########");
			}
			System.out.println();
			Scanner scan = new Scanner(System.in);
			for(;;){
					System.out.print("Please enter: ");
					String input = scan.nextLine();
					if(input.equalsIgnoreCase("x")) break;
					String ahref = getAhref(input);
					System.out.println();
					System.out.print(ahref);
					System.out.println();
			}
	}

	private static String getAhref ( String input ) {
			// TODO: Implement this method
			AhrefFromHTML ahfh = null ;
			if(input.contains("<a") && input.contains("href=\"") && input.contains(">") && input.contains("</a>")) ahfh = new AhrefFromHTML(input);
			else{
					File f = new File(input);
					if(f.isFile()){
							ahfh = new AhrefFromHTML(f);
					} else {
							try {
									URL url = new URL ( input );
									ahfh = new AhrefFromHTML(url);
							} catch (MalformedURLException e) {
									return "Invalid input";
							}
					}
			}
			return ahfh.toString();
	}

	private static String formattedWM ( String wmsg ) {
			// TODO: Implement this method
			String wm = "";
			String[] ss = wmsg.split("\n");
			for(String s:ss){
					s="# "+s;
					int diff = 44-s.length();
					for(int i=1;i<diff;i++){
							s+=" ";
					}
					s+="#";
					wm+=("\n"+s);
			}
			return wm;
	}
}
