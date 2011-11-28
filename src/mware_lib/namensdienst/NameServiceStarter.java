package mware_lib.namensdienst;


public class NameServiceStarter {

	public static void main(String[] args) {

		if (args.length>0 && !args[0].equals("--help")) {
			try {
				NameServiceServer nsServer = new NameServiceServer(Integer.parseInt(args[0]));
				nsServer.start();
			} catch (NumberFormatException ex) {
				System.err.println("Usage: java mware_lib.namensdienst <name-service-port>");
			}
		} else {
			System.err.println("Usage: java mware_lib.namensdienst <name-service-port>");
		}
	}
}
