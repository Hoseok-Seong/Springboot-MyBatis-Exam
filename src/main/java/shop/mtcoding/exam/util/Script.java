package shop.mtcoding.exam.util;

public class Script {
    public static String back(String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }

    public static String href(String msg, String path) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("location.href = '" + path + "';"); // 그 페이지로 이동
        sb.append("</script>");
        return sb.toString();
    }

    public static String path(String path) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("location.href = '" + path + "';"); // 그 페이지로 이동
        sb.append("</script>");
        return sb.toString();
    }
}
