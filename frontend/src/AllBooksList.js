import React, { Component } from 'react';
import BooksList from "./BooksList";

class AllBooksList extends Component {

    render() {
        return (
            <BooksList path="/books" headline="All Books"/>
        )
    }

}

export default AllBooksList;