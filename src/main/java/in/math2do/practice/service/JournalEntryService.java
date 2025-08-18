package in.math2do.practice.service;

import in.math2do.practice.entity.JournalEntry;
import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {
  @Autowired
  private JournalEntryRepository journalRepository;

  @Autowired
  private UserService userService;

  @Transactional
  public void saveJournalEntry(JournalEntry journalEntry, String username) {
    UserEntity user = this.userService.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    journalEntry.setDate(LocalDateTime.now());
    JournalEntry savedJournal = this.journalRepository.save(journalEntry);

    user.getJournals().add(savedJournal);
    userService.saveUser(user);
  }

  public void saveJournalEntry(JournalEntry journalEntry) {
    journalEntry.setDate(LocalDateTime.now());
    this.journalRepository.save(journalEntry);
  }

  public List<JournalEntry> getAllEntries() {
    return this.journalRepository.findAll();
  }

  public Optional<JournalEntry> getEntryById(ObjectId id) {
    log.info("journalId received: {}", id);
    return this.journalRepository.findById(id);
  }

  public void deleteById(ObjectId id, String username) {
    UserEntity user = userService.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    ;
    user.getJournals().removeIf(entry -> entry.getId().equals(id));

    userService.saveUser(user);
    this.journalRepository.deleteById(id);
  }
}
