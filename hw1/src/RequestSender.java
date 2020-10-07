import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RequestSender {
    private static final String part1 = "curl 'http://1d3p.wp.codeforces.com/new'" +
            " -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:77.0) Gecko/20100101 Firefox/77.0'" +
            " -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8'" +
            " -H 'Accept-Language: en-US,en;q=0.5'" +
            " --compressed -H 'Content-Type: application/x-www-form-urlencoded'" +
            " -H 'Origin: http://1d3p.wp.codeforces.com'" +
            " -H 'Connection: keep-alive'" +
            " -H 'Referer: http://1d3p.wp.codeforces.com/'" +
            " -H 'Cookie: __utma=71512449.102682541.1584013359.1599828920.1599922001.9;" +
            " __utmz=71512449.1590233582.4.4.utmcsr=yandex.ru|utmccn=(referral)|utmcmd=referral|utmcct=/;" +
            " 70a7c28f3de=kxvox0gx5m0nxhy369; __utmc=71512449; evercookie_png=kxvox0gx5m0nxhy369;" +
            " evercookie_etag=kxvox0gx5m0nxhy369; evercookie_cache=kxvox0gx5m0nxhy369;" +
            " JSESSIONID=F8491137913FE46C433482D5D3253F9F'" +
            " -H 'Upgrade-Insecure-Requests: 1'" +
            " -H 'Pragma: no-cache'" +
            " -H 'Cache-Control: no-cache'" +
            " --data-raw '_af=34be50b38beccce4&proof=";
    private static final String part2 = "&amount=";
    private static final String part3 = "&submit=Submit'";

    private static String getCommand(int i) {
        return part1 + (i * i) + part2 + i + part3;
    }

    public static void main(String[] args) {
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(new File("script.sh"))))) {
            for (int i = 1; i < 101; i++) {
                out.write(getCommand(i));
                if (i < 100) {
                    out.write('\n');
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
