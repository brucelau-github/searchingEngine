package analysis;

import java.util.ArrayList;

import database.*;
//analyse one document, we should have another class to analyse multi class.
public class InvertedIndex { 
	private String doc = "";
	private DAO dao;
	private AnalyserDAO aDao = new AnalyserDAO();
	private Analyser analyse;
	public InvertedIndex(){
		dao = new RedisDAO();
		
	}
	public void retreivedFile(){
		long docID = Long.parseLong(aDao.getDocID());
		analyse = new ScannerAnalysis(docID);
//		//to do :
//		// check list "DocumentKeys" llen > 0;
//		// lpop(DocumentKeys);
//		// doc = get(docKey);
//		dao.set("doc:2", "By late 2006, Google established a new headquarters for its AdWords division in Ann Arbor, Michigan.[300] In November 2006, Google opened offices on Carnegie Mellon's campus in Pittsburgh, focusing on shopping-related advertisement coding and smartphone applications and programs.[301][302] Other office locations in the U.S. include Atlanta, Georgia; Austin, Texas; Boulder, Colorado; Cambridge, Massachusetts; San Francisco, California; Seattle, Washington; Reston, Virginia, and Washington, D.C.[303]");
//		dao.set("doc:2", "The Second Test match was held at Lord's, but Miller was still unable to bowl when it began.[68] He came in to bat in the first innings with Australia at 3/166 on the first afternoon after electing to bat.[68] Bedser bowled three consecutive outswingers; the fourth ball swung the other way, and Miller was hit on the pads not offering a shot, believing that the ball would have curved away past the stumps. The umpire upheld England’s appeal for leg before wicket (lbw) and Miller was out for four.[71] O'Reilly blamed Miller's poor form with the bat on an excessive bowling workload imposed on him by Bradman.[72] Australia made 350, but suffered a blow when Lindwall's injury flared up in the first over; Lindwall played on through the pain. Bradman threw Miller the ball, hoping that the all rounder would reverse his decision not to bowl and take inspiration from Lindwall. The injured bowler returned the ball, citing his back. His gesture generated news headlines among journalists who believed that he had disobeyed Bradman.[71][73]"
//				+ "Although Bradman claimed that the exchange had been amicable, others disputed this. Teammate Barnes later claimed that Miller had responded to Bradman that he—a very occasional slower bowler—bowl himself. Barnes said that the captain \"was as wild as a battery-stung brumby\" and warned his unwilling bowler that there would be consequences for his defiance.[74] In unpublished writings in his personal collection, Fingleton recorded that Bradman chastised his players in the dressing room at the end of the play, saying \"I'm 40 and I can do my full day's work in the field.\"[75] According to Fingleton, Miller snapped in reply: \"So would I—if I had fibrositis\".[75][notes 3]"
//						+ "England fell to 4/46 after Lindwall and Johnston's new ball burst, but Compton and Yardley fought back to take the score to 133 without further loss.[77] Compton edged Johnston into the slips, where Miller took a low catch, dismissing the batsman for 53.[78] Soon after, Johnston removed Evans for nine, caught by a diving Miller after lashing out at a wide ball outside off stump to leave the hosts at 7/145.[77][79] Australia bowled England out for 215 at the beginning of the third day to take a 135-run first innings lead. This had increased to 431 when Miller came to the crease with the score at 3/296 during the afternoon. English captain Yardley was on a hat-trick, having removed Hassett first ball after the fall of Barnes. Miller survived a loud lbw appeal on the hat-trick ball before hitting a six into the grandstand and reaching stumps on 22, with Australia at 4/329. He resumed on the fourth morning and reached lunch on 63 with the tourists at 4/409. Miller was reprieved when Tom Dollery dropped a catch from a ball he skied into the air.[80] When the new ball was taken, and Miller hit three boundaries to pass 50, to lift the run rate.[81] Miller repeatedly hooked Coxon and drove Bedser for many runs.[82] After lunch, he hit out at every opportunity before the declaration. He was out for 74, playing a hook shot that was caught by Bedser at square leg from the bowling of Laker. Australia declared at 7/460 to set England a target of 596.[83]");
//		doc = dao.get("doc:2");
		doc = aDao.getDoc(docID);
	}
	/* (non-Javadoc)
	 * @see analysis.analyzable#analyseDocument()
	 */

	public void analyse(){

		analyse.analyse(doc);
		analyse.print();
		analyse.save();
	}

    public static void main(String[] args) {
    	
    	InvertedIndex inv = new InvertedIndex();
    	inv.retreivedFile();
    	inv.analyse();
    	inv.dumpBackToDataBase();
    	

//        // key = word, value = set of filenames containing that word
//        ST<String, SET<String>> st = new ST<String, SET<String>>();
//
//        // create inverted index of all files
//        for (String filename : args) {
//            // StdOut.println(filename);
//            In in = new In(filename);
//            while (!in.isEmpty()) {
//                String word = in.readString();
//                if (!st.contains(word)) st.put(word, new SET<String>());
//                SET<String> set = st.get(word);
//                set.add(filename);
//            }
//        }
//
//        // read queries from standard input, one per line
//        while (!StdIn.isEmpty()) {
//            String query = StdIn.readString();
//            if (!st.contains(query)) StdOut.println("NOT FOUND");
//            else {
//                SET<String> set = st.get(query);
//                for (String filename : set) {
//                    StdOut.print(filename + " ");
//                }
//                StdOut.println();
//            }
//            StdOut.println();
//        }

    }
	private void dumpBackToDataBase() {
		// TODO Auto-generated method stub
		
	}

}


