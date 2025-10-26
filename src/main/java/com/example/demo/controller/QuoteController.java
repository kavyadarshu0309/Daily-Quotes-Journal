package com.example.demo.controller;
import com.example.demo.repository.QuoteRepository;


import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Quote;
// duplicate import removed

@RestController
@RequestMapping("/quotes")
@CrossOrigin("*")
public class QuoteController {

    @Autowired
    private QuoteRepository repo;

    @GetMapping
    public List<Quote> getAllQuotes() {
        return repo.findAll();
    }

    @PostMapping
    public Quote addQuote(@RequestBody Quote quote) {
        return repo.save(quote);
    }
    

    @DeleteMapping("/{id}")
    public void deleteQuote(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @GetMapping("/random")
    public Quote getRandomQuote() {
    List<Quote> quotes = repo.findAll();
    if (quotes.isEmpty()) {
        return new Quote("No quotes found! Please add some first.", "System");
    }
    Random random = new Random();
    return quotes.get(random.nextInt(quotes.size()));
}
    // Get total count
@GetMapping("/count")
public long getQuoteCount() {
    return repo.count();
}

// Toggle favorite
@PutMapping("/{id}/favorite")
public Quote toggleFavorite(@PathVariable Long id) {
    Quote quote = repo.findById(id).orElseThrow();
    quote.setFavorite(!quote.isFavorite());
    return repo.save(quote);
}

// Get all favorites
@GetMapping("/favorites")
public List<Quote> getFavorites() {
    return repo.findAll().stream()
        .filter(Quote::isFavorite)
        .toList();
}
}
