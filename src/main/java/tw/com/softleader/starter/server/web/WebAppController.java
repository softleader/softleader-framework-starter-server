package tw.com.softleader.starter.server.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tw.com.softleader.commons.compress.ArchiveStream;
import tw.com.softleader.starter.server.entity.Webapp;
import tw.com.softleader.starter.server.pojo.Snippet;
import tw.com.softleader.starter.server.service.ModuleService;
import tw.com.softleader.starter.server.service.WebAppService;

@Slf4j
@RestController
@RequestMapping("/webapp")
public class WebAppController {

  @Autowired
  private ModuleService moduleService;

  @Autowired
  private WebAppService webAppService;


  @RequestMapping(method = RequestMethod.GET)
  public Webapp getWebApp() {
    return webAppService.getAvailableOne();
  }

  @RequestMapping(value = "/zip", method = RequestMethod.POST, consumes = "application/json",
      produces = "application/zip")
  public void getZip(@RequestBody @Validated Snippet snippet, HttpServletResponse response)
      throws IOException, ArchiveException {
    log.info("{}", new ObjectMapper().writeValueAsString(snippet));
    Map<ZipArchiveEntry, InputStream> archives = moduleService.collectSnippets(snippet);
    ArchiveStream.of(response.getOutputStream()).compress(archives);
  }

}
