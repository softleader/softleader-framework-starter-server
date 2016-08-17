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
import tw.com.softleader.starter.server.entity.Starter;
import tw.com.softleader.starter.server.pojo.Snippet;
import tw.com.softleader.starter.server.service.ModuleService;
import tw.com.softleader.starter.server.service.StarterService;

@Slf4j
@RestController
@RequestMapping("/starter")
public class StarterController {

  @Autowired
  private ModuleService moduleService;

  @Autowired
  private StarterService starterService;


  @RequestMapping(method = RequestMethod.GET)
  public Starter starter() {
    return starterService.getAvailableOne();
  }

  @RequestMapping(value = "/zip", method = RequestMethod.POST, consumes = "application/json",
      produces = "application/zip")
  public void zip(@RequestBody @Validated Snippet snippet, HttpServletResponse response)
      throws IOException, ArchiveException {
    log.info("{}", new ObjectMapper().writeValueAsString(snippet));
    Map<ZipArchiveEntry, InputStream> archives = moduleService.collectSnippets(snippet);
    ArchiveStream.of(response.getOutputStream()).compress(archives);
  }

}
