import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

class BooksList extends Component {

    constructor(props) {
        super(props);
        this.state = {books: []};
        this.remove = this.remove.bind(this);
        this.path = props.path
        this.headline = props.headline

    }

    componentDidMount() {
        fetch(this.props.path)
            .then((response) => {
                if(!response.ok) throw new Error(response.status);
                else return response.json();
            })
            .then(data => this.setState({books: data}))
            .catch((error) => {
                alert(error);
                this.setState({ requestFailed: true });
            });
    }

    async remove(id) {
        await fetch(`/books/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedBooks = [...this.state.books].filter(i => i.id !== id);
            this.setState({books: updatedBooks});
        });
    }

    async returnBook(id) {
        await fetch(`/books/return-book/${id}`, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            if(!response.ok) throw new Error(response.status);
            else return response.json();
          })
          .then((data) => {
            this.updateBook(data)
          })
          .catch((error) => {
            alert(error);
            this.setState({ requestFailed: true });
          });
    }

    updateBook(updatedBook) {
        const index = this.state.books.findIndex(book => {
            return book.id === updatedBook.id;
          });
        let updatedBooks = [...this.state.books];
        updatedBooks[index] = updatedBook;
        this.setState({books: updatedBooks});
    }

    isLoaned(book) {
        return book.loan && book.loan.borrowedFrom
    }

    getReturnOrLoanButton(book) {
        if (this.isLoaned(book)) {
            return (
                <Button size="sm" onClick={() => this.returnBook(book.id)}>Return</Button>
            )
        } else {
            return (
                <Button size="sm" tag={Link} to={"/books/loan/" + book.id}>Loan</Button>
            )
        }
    }

    render() {
        const {books} = this.state;


        const booksList = books.map(book => {



            return <tr key={book.id}>
                <td style={{whiteSpace: 'nowrap'}}>{book.name}</td>
                <td>{book.author}</td>
                <td>{this.isLoaned(book) ? "Yes" : "No"}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/books/edit/" + book.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(book.id)}>Delete</Button>
                        {this.getReturnOrLoanButton(book)}
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/books/new/new">Add Book</Button>
                    </div>
                    <h3>{this.props.headline}</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="25%">Name</th>
                            <th width="25%">Author</th>
                            <th width="25%">Loaned</th>
                            <th width="25%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {booksList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default BooksList;