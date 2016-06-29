//BAYES DECISION NET

import norsys.netica.Environ;
import norsys.netica.Net;
import norsys.netica.NeticaException;
import norsys.neticaEx.aliases.Node;

public class DecisionMaker {
	
	// DECISION - Publicly accessible class with results of each net computation
	public static class Decision {
		boolean tryAsk; // True if utility of Ask was greater
		float probSuccess; // Conditional probability of successful transfer
		
		public Decision(boolean tryAsk, float probSuccess){
			this.tryAsk=tryAsk;
			this.probSuccess=probSuccess;
		}
	}

	private static DecisionMaker singleton = new DecisionMaker();
	private Net net;
	private Node resources, friends, acquaintance, result, seek, utility;

	private DecisionMaker() {
		try {
			// Set up environment
			Node.setConstructorClass("norsys.neticaEx.aliases.Node");
			Environ env = new Environ ("+KannoT/UTokyo/120,310-6-A/23000");
			net = new Net();
			net.setName("ResourceDistribution");

			// Create nodes
			resources = new Node("Resources", "many, few", net);
			friends = new Node("Friends", "yes, no", net);
			acquaintance = new Node("Acquaintance", "yes, no", net);
			result = new Node("Result", "success,failure", net);
			seek = new Node("Seek", "ask, ignore", net);
			utility = new Node("Utility", 0, net);

			// Set node types
			seek.setKind(Node.DECISION_NODE);
			utility.setKind(Node.UTILITY_NODE);

			// Create links between nodes
			utility.addLink(resources);
			utility.addLink(friends);
			utility.addLink(result);
			result.addLink(friends);
			result.addLink(resources);
			result.addLink(seek);
			friends.addLink(acquaintance);

			// Set initial probabilities
			resources.setCPTable(0.2, 0.8);
			acquaintance.setCPTable(0.5, 0.5);

			// friends
			// acquaintance | yes or no
			friends.setCPTable("yes", 0.7, 0.3);
			friends.setCPTable("no", 0, 1);

			// result
			// friends, resources, share | success or failure
			result.setCPTable("yes, many, ask", 0.65, 0.35);
			result.setCPTable("no, many, ask", 0.25, 0.75);
			result.setCPTable("yes, few, ask", 0.80, 0.20);
			result.setCPTable("no, few, ask", 0.45, 0.55);
			result.setCPTable("yes, many, ignore", 0, 1);
			result.setCPTable("no, many, ignore", 0, 1);
			result.setCPTable("yes, few, ignore", 0, 1);
			result.setCPTable("no, few, ignore", 0, 1);

			// resources, friends, result
			utility.setRealFuncTable("many, yes, success", 80);
			utility.setRealFuncTable("many, yes, failure", 50);
			utility.setRealFuncTable("many, no,  success", 70);
			utility.setRealFuncTable("many, no,  failure", 20);
			utility.setRealFuncTable("few, yes, success", 100);
			utility.setRealFuncTable("few, yes, failure", 30);
			utility.setRealFuncTable("few, no,  success", 100);
			utility.setRealFuncTable("few, no,  failure", 0);

			net.compile(); // Compiles finished net

		} catch (NeticaException e) {
			e.printStackTrace();
		}
	}

	// INTEGRATION WITH SIMULATION VIA METHOD
	private Decision decide(int pResource, boolean pAcquaintance) {
		try {
			// Classifies Resource Level
			if (pResource > 5) {
				resources.finding().enterState("many");
			} else {
				resources.finding().enterState("few");
			}
			// Classifies Acquaintance Status
			if (pAcquaintance = true) {
				acquaintance.finding().enterState("yes");
			} else {
				acquaintance.finding().enterState("no");
			}

			// Gets expected utilities of seeking/ignoring
			float[] utils = seek.getExpectedUtils();

			// Adds probability of success to returned array
			float success = result.getBelief("success");

			// Clears up the inputted values
			net.retractFindings();
			
			// Creates new Decision object with results as attributes
			return new Decision(utils[0]>utils[1],success);
			
		} catch (NeticaException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Public method to send only results to the rest of the simulation
	public static Decision makeDecision(int pResource, boolean pAcquaintance) {
		return singleton.decide(pResource, pAcquaintance);
	
//		try {
//
//			// set up environment
//			Node.setConstructorClass("norsys.neticaEx.aliases.Node");
//			Environ env = new Environ("+KannoT/UTokyo/120,310-6-A/23000");
//			Net net = new Net();
//			net.setName("ResourceDistribution");
//
//			// create nodes
//			Node Resources = new Node("Resources", "many, few", net);
//			Node Friends = new Node("Friends", "yes, no", net);
//			Node Acquaintance = new Node("Acquaintance", "yes, no", net);
//			Node Result = new Node("Result", "success,failure", net);
//			Node Seek = new Node("Seek", "ask, ignore", net);
//			Node Utility = new Node("Utility", 0, net);
//
//			// set node types
//			Seek.setKind(Node.DECISION_NODE);
//			Utility.setKind(Node.UTILITY_NODE);
//
//			// create links
//			Utility.addLink(Resources);
//			Utility.addLink(Friends);
//			Utility.addLink(Result);
//			Result.addLink(Friends);
//			Result.addLink(Resources);
//			Result.addLink(Seek);
//			Friends.addLink(Acquaintance);
//
//			Resources.setCPTable(0.2, 0.8);
//			Acquaintance.setCPTable(0.5, 0.5);
//
//			// friends
//			// acquaintance | yes or no
//			Friends.setCPTable("yes", 0.7, 0.3);
//			Friends.setCPTable("no", 0, 1);
//
//			// result
//			// friends, resources, share | success or failure
//			Result.setCPTable("yes, many, ask", 0.65, 0.35);
//			Result.setCPTable("no, many, ask", 0.25, 0.75);
//			Result.setCPTable("yes, few, ask", 0.80, 0.20);
//			Result.setCPTable("no, few, ask", 0.45, 0.55);
//			Result.setCPTable("yes, many, ignore", 0, 1);
//			Result.setCPTable("no, many, ignore", 0, 1);
//			Result.setCPTable("yes, few, ignore", 0, 1);
//			Result.setCPTable("no, few, ignore", 0, 1);
//
//			// resources, friends, result
//			Utility.setRealFuncTable("many, yes, success", 80);
//			Utility.setRealFuncTable("many, yes, failure", 50);
//			Utility.setRealFuncTable("many, no,  success", 70);
//			Utility.setRealFuncTable("many, no,  failure", 20);
//			Utility.setRealFuncTable("few, yes, success", 100);
//			Utility.setRealFuncTable("few, yes, failure", 30);
//			Utility.setRealFuncTable("few, no,  success", 100);
//			Utility.setRealFuncTable("few, no,  failure", 0);
//
//			net.compile();
//
//			// INTEGRATION
//
//			// Classifies Resources Level
//			if (pResource > 5) {
//				Resources.finding().enterState("many");
//			} else {
//				Resources.finding().enterState("few");
//			}
//			// Classifies Acquaintance Status
//			if (pAcquaintance = true) {
//				Acquaintance.finding().enterState("yes");
//			} else {
//				Acquaintance.finding().enterState("no");
//			}
//			// Gets expected utilities of seeking/ignoring
//			float[] utils = Seek.getExpectedUtils();
//
//			// Adds probability of success to returned array
//			utils[2] = Result.getBelief("success");
//
//			// Clears up memory
//			net.retractFindings();
//			net.finalize();
//
//			return utils;
//
//			// --- 1st type of usage: To get the expected utilities, given the
//			// current findings
//			// float[] utils = Seek.getExpectedUtils();
//			// returns expected utilities, given current findings
//
//			// Resources.finding().enterState ("many");
//			// Acquaintance.finding().enterState ("yes");
//
//			// System.out.println ("The probability of success is " +
//			// Result.getBelief("success"));
//			// System.out.print ("If you are acquainted and have many resources,
//			// ");
//			// System.out.println ("the expected utility of " + Seek.state(0) +
//			// " is " + utils[0] +
//			// ", of " + Seek.state(1) + " is " + utils[1] + "\n");
//
//			// net.retractFindings();
//			// Resources.finding().enterState ("few");
//			// Acquaintance.finding().enterState ("yes");
//			// utils = Seek.getExpectedUtils();
//
//			// System.out.println ("The probability of success is " +
//			// Result.getBelief("success"));
//			// System.out.print ("If you are acquainted and have few resources,
//			// ");
//			// System.out.println ("the expected utility of " + Seek.state(0) +
//			// " is " + utils[0] +
//			// ", of " + Seek.state(1) + " is " + utils[1] + "\n");
//
//			// net.retractFindings();
//			// Resources.finding().enterState ("many");
//			// Acquaintance.finding().enterState ("no");
//			// utils = Seek.getExpectedUtils();
//
//			// System.out.println ("The probability of success is " +
//			// Result.getBelief("success"));
//			// System.out.print ("If you are not acquainted and have many
//			// resources, ");
//			// System.out.println ("the expected utility of " + Seek.state(0) +
//			// " is " + utils[0] +
//			// ", of " + Seek.state(1) + " is " + utils[1] + "\n");
//
//			// net.retractFindings();
//			// Resources.finding().enterState ("few");
//			// Acquaintance.finding().enterState ("no");
//			// utils = Seek.getExpectedUtils();
//
//			// System.out.println ("The probability of success is " +
//			// Result.getBelief("success"));
//			// System.out.print ("If you are not acquainted and have few
//			// resources, ");
//			// System.out.println ("the expected utility of " + Seek.state(0) +
//			// " is " + utils[0] +
//			// ", of " + Seek.state(1) + " is " + utils[1] + "\n");
//
//			// net.finalize(); // free resources immediately and safely; not
//			// strictly necessary, but a good habit
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
	}
}
