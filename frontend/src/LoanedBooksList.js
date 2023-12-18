import React, { Component } from 'react';
import BooksList from "./BooksList";

class LoanedBooksList extends Component {

    render() {
        return (
            <BooksList path="/books/loaned-books" headline="Loaned Books"/>
        )
    }

}

export default LoanedBooksList;