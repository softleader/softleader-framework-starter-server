package tw.com.softleader.starter.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.com.softleader.data.entity.EntityJsonIgnore;
import tw.com.softleader.data.entity.GenericEntity;

@SuppressWarnings("serial")
@Setter
@Getter
@Entity
@Table(name = "SOURCE")
@NoArgsConstructor
@AllArgsConstructor
public class Source extends GenericEntity<Long> implements EntityJsonIgnore<Long> {

  public Source(String path) {
    super();
    this.path = path;
  }

  @JsonBackReference("sources")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MODULE_ID")
  private Module module;

  @Column(name = "PATH")
  private String path;

  @Column(name = "ENTRY_NAME")
  private String entryName; // 當 path 是一的檔案時才會且必須參考

  @Column(name = "APPEND_DEFAULT_ROOT")
  private boolean appendDefaultRoot = true; // 是否要加上定義在 properties 中的 source.root

  @Transient
  private transient String root;

  public String getFullPath() {
    if (appendDefaultRoot) {
      return FilenameUtils.normalize(root + "/" + path, true);
    }
    return path;
  }

}
