import com.starnet.musicplayer.utils.QiniuUtils;
import org.junit.jupiter.api.Test;

/**
 * @author Planeter
 * @description: TODO
 * @date 2023/2/16 11:21
 * @status dev
 */
public class QiniuTest {
    @Test
    public void uploadTest() {
        String url = QiniuUtils.upload("E:/Projects/IDEA/MySQL/src/main/java/ManagementSystem.java","ManagementSystem.java");
        System.out.println(url);
    }
}
