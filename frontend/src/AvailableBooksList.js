import React, { Component } from 'react';
import BooksList from "./BooksList";

class AvailableBooksList extends Component {

    render() {
        return (
            <BooksList path="/books/available-books" headline="Available Books"/>
        )
    }

}

export default AvailableBooksList;