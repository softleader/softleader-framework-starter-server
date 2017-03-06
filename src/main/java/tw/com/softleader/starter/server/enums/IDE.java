package tw.com.softleader.starter.server.enums;

import java.util.function.Predicate;

import lombok.Getter;

/**
 * 最開始這個 server 端專門 for eclipse plugin 使用, 所以會放到一些 eclipse 專用的檔案<p>
 * </p>但後來 client 端會不同(如 intellij), 這些 eclipse 專用的檔案就不用了
 *
 * @author Matt S.Y Ho
 */
public enum IDE {
    GLOBAL(f -> !f.contains(".settings") && !f.contains(".classpath")),
    ECLIPSE(f -> true),;

    IDE(Predicate<String> fileFilter) {
        this.fileFilter = fileFilter;
    }

    @Getter
   private final Predicate<String> fileFilter;
}
