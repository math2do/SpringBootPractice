package in.math2do.practice.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import in.math2do.practice.entity.*;
import in.math2do.practice.service.*;

@RestController
@RequestMapping("/journals")
public class JournalEntryController {

  @Autowired
  private JournalEntryService journalEntryService;

  @Autowired
  private UserService userService;

  @GetMapping("/all/{username}")
  public ResponseEntity<List<JournalEntry>> getEntries(@PathVariable String username) {
    UserEntity user = userService.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    List<JournalEntry> entries = user.getJournals();
    return ResponseEntity.ok().body(entries);
  }

  @GetMapping("/{id}")
  public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id) {
    Optional<JournalEntry> journal = this.journalEntryService.getEntryById(id);
    return journal.map(journalEntry -> ResponseEntity.ok().body(journalEntry))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{username}")
  public ResponseEntity<JournalEntry> setEntry(@RequestBody JournalEntry journalEntry,
      @PathVariable String username) {
    try {
      this.journalEntryService.saveJournalEntry(journalEntry, username);
      URI location = URI.create("/journal/" + journalEntry.getId().toString());
      return ResponseEntity.created(location).body(journalEntry);
    } catch (Exception ex) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{username}/{id}")
  public ResponseEntity<?> deleteEntry(@PathVariable String username, @PathVariable ObjectId id) {
    this.journalEntryService.deleteById(id, username);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{username}/{id}")
  public ResponseEntity<JournalEntry> updateEntry(@PathVariable String username,
      @PathVariable ObjectId id, @RequestBody JournalEntry journalEntryReq) {
    JournalEntry oldEntry = journalEntryService.getEntryById(id).orElse(null);

    // Update the old entry with passed new values and save in db
    if (oldEntry != null) {

      oldEntry.setTitle(journalEntryReq.getTitle() != null && !journalEntryReq.getTitle().isEmpty()
          ? journalEntryReq.getTitle()
          : oldEntry.getTitle());

      oldEntry.setContent(
          journalEntryReq.getContent() != null && !journalEntryReq.getContent().isEmpty()
              ? journalEntryReq.getContent()
              : oldEntry.getContent());

      oldEntry.setDate(LocalDateTime.now());
      this.journalEntryService.saveJournalEntry(oldEntry);
    }

    return ResponseEntity.noContent().build();
  }
}
