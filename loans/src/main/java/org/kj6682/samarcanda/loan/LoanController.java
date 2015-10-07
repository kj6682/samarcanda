package org.kj6682.samarcanda.loan;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;

/**
 * Created by luigi on 30/09/15.
 */
@RestController
public class LoanController {

    @Inject
    private LoanRepository loanRepository;

    @RequestMapping(value = "/loans", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Loan>> getAllLoans() {

        Iterable<Loan> loans = loanRepository.findAll();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/loans", method = RequestMethod.POST)
    public ResponseEntity<?> createloan(@RequestBody Loan loan){

        loan = loanRepository.save(loan);

        //Set the loan header for the newly created loan
        HttpHeaders responseHeaders = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(loan.getId())
                .toUri();
        responseHeaders.setLocation(uri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/loans/{loanId}", method = RequestMethod.GET)
    public ResponseEntity<?> getloan(@PathVariable Long loanId) {
        Loan p = loanRepository.findOne(loanId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @RequestMapping(value="/loans/{loanId}", method=RequestMethod.PUT)
    public ResponseEntity<?> updateloan(@RequestBody Loan loan, @PathVariable Long loanId) {
        // Save the entity
        loanRepository.save(loan);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/loans/{loanId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteloan(@PathVariable Long loanId) {
        loanRepository.delete(loanId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
