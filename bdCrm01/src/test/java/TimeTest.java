import com.bjpowernode.crm.util.DateTimeUtil;
public class TimeTest {
    public static void main(String[] args) {
//        String time = DateTimeUtil.getSysTime();
//        System.out.println(time);
//
//        String myTime = "2015-10-19 19:18:18";
//        System.out.println(time.compareTo(myTime));

//        String test1 = "boo:ools:kkkkkjl";
//        String[] lstrings = test1.split("k" ) ;
//        for (String ele:lstrings){
//            System.out.println(ele);
//        }
        String test1 = "boo:and:foool";
        String[] lstrings = test1.split("o",6) ;
        for (String ele:lstrings){
            System.out.println(ele);
        }

        System.out.println(1);
    }
}