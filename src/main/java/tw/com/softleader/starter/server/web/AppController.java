package tw.com.softleader.starter.server.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tw.com.softleader.commons.compress.ZipStream;
import tw.com.softleader.starter.server.pojo.Starter;
import tw.com.softleader.starter.server.service.ModuleService;

@RestController
@RequestMapping("/app")
public class AppController {

  @Autowired
  private ModuleService service;

  @RequestMapping(value = "/zip", method = RequestMethod.POST, consumes = "application/json",
      produces = "application/zip")
  public void zip(@RequestBody @Validated Starter starter, HttpServletResponse response)
      throws IOException {
    Map<ZipArchiveEntry, byte[]> archives = service.collectSnippets(starter);
    ZipStream.of(response.getOutputStream()).compress(archives);
  }

}
