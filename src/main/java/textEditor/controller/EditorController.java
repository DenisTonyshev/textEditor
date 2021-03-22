package textEditor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import textEditor.exceptions.LengthException;
import textEditor.exceptions.MixedUpException;
import textEditor.services.IEditorService;

@Slf4j
@RestController
public class EditorController {

    @Autowired
    IEditorService editorService;

    @GetMapping(value = "/print")
    String print() {
        log.info("/print");
        return editorService.print();
    }
    @GetMapping(value = "/undo")
    String undo() {
        log.info("/undo");
        return editorService.undo();
    }
    @GetMapping(value = "/redo")
    String redo() {
        log.info("/redo");
        return editorService.redo();
    }

    @PostMapping(value = "/add")
    String add(@RequestParam(value = "text") String s, @RequestParam(value = "index", required = false) Integer i) {
        if (i != null) {
            log.info("/add: {} {}", s, i);
            return editorService.add(s, i);
        } else {
            log.info("/add: {}", s);
            return editorService.add(s);
        }
    }

    @PostMapping(value = "/italic")
    String italic(@RequestParam(value = "from") int from, @RequestParam(value = "to") int to) throws MixedUpException, LengthException {
        log.info("/italic {} {}", from, to);
        return editorService.italic(from, to);
    }

    @PostMapping(value = "/bold")
    String bold(@RequestParam(value = "from") int from, @RequestParam(value = "to") int to) throws MixedUpException, LengthException  {
        log.info("/bold {} {}", from, to);
        return editorService.bold(from, to);
    }

    @PostMapping(value = "/underline")
    String underline(@RequestParam(value = "from") int from, @RequestParam(value = "to")  int to) throws MixedUpException, LengthException  {
        log.info("/underline {} {}", from, to);
        return editorService.underline(from, to);
    }

    @PostMapping("/remove")
    String remove(@RequestParam(value = "from") int from, @RequestParam(value = "to") int to) throws MixedUpException, LengthException  {
        log.info("/remove {} {}", from, to);
        return editorService.remove(from, to);
    }

}
