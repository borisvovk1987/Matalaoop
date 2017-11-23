import java.io.IOException;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String s="c:\\Users\\Boris_Vovk\\input_output";                            // התקיה שמכילה את קבציה וויגל המקוריים 
		String s2="c:\\Users\\Boris_Vovk\\testttt\\test.csv";                      // תיקיה עם הקובץ שרוצים ליצר
		CsvToCsv csv=new CsvToCsv(s,s2);
		csvToKml cToK=new csvToKml(s2,"ID","model=LG-H850");
	


	}

}
