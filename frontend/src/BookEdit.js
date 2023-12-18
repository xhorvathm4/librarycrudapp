import * as yup from 'yup';
import React, { Component } from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { Button, Container, FormGroup, Label } from 'reactstrap';
import { Form, Input } from '@availity/form';
import { DateField } from '@availity/date';
import '@availity/date/styles.scss';

function withParams(Component) {
    return props => <Component {...props} params={useParams()} navigate={useNavigate()}/>;
  }

class BookEdit extends Component {

    emptyItem = {
        name: '',
        author: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleAddOrEdit = this.handleAddOrEdit.bind(this);
        this.handleLoan = this.handleLoan.bind(this);
        this.submit = this.submit.bind(this);
    }

    async componentDidMount() {
        if (this.props.params.action === 'edit' || this.props.params.action === 'loan') {
            const book = await (await fetch(`/books/${this.props.params.id}`)).json();
            if (!book) {
                alert('Book with specified ID does not exist')
            } else {
                this.setState({item: book});
            }
        }
    }

    handleAddOrEdit(values) {
        let item = {...this.state.item};
        item.name = values.name;
        item.author = values.author;
        this.setState({item}, this.submit);
    }

    handleLoan(values) {
        let item = {...this.state.item};
        if (!item.loan) {
            let person = {
                firstName: values.firstName,
                lastName: values.lastName
            };
            let loan = {
                person: person,
                borrowedFrom: values.borrowedFrom
            };
            item.loan = loan;
        }
        
        this.setState({item}, this.submit);
    }

    async submit() {
        const {item} = this.state;

        let url;
        if (this.props.params.action === 'loan') {
            url = '/books/loan-book/' + item.id
        } else if (this.props.params.action === 'edit') {
            url = '/books/' + item.id
        } else if (this.props.params.action === 'new') {
            url = '/books'
        }

        await fetch(url, {
            method: (this.props.params.action !== 'new') ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.navigate('/books');
    }

    addOrEditDialog(item) {
        let headline = '';
        if (this.props.params.action === 'edit') {
            headline = <h2>{'Edit Book'}</h2>;
        } else if (this.props.params.action === 'new') {
            headline = <h2>{'New Book'}</h2>;
        }

        return (
            <div>
            <Container>
                {headline}
                <Form
                    enableReinitialize
                    initialValues={{
                        author: this.state.item.author,
                        name: this.state.item.name
                    }}
                    onSubmit={(values) => this.handleAddOrEdit(values)}
                    validationSchema={yup.object().shape({
                        name: yup.string().required().max(15),
                        author: yup.string().required(),
                    })}
                >   
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input name="name" feedback required/>
                    </FormGroup>
 
                    <FormGroup>
                        <Label for="author">Author</Label>
                        <Input name="author" required />
                    </FormGroup>

                    <FormGroup>
                        <Button type="submit" className="ml-1" color="primary">
                            Submit
                        </Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
        )
    }

   loanDialog(item) {
        return (
            <div>
            <Container>
                <h2>Loan Book</h2>

                <Form
                    initialValues={{
                        firstName: '',
                        lastName: '',
                        borrowedFrom: new Date()
                    }}
                    onSubmit={(values) => this.handleLoan(values)}
                    validationSchema={yup.object().shape({
                        firstName: yup.string().required(),
                        lastName: yup.string().required(),
                        borrowedFrom: yup.date()
                                         .typeError('The value must be a date (YYYY-MM-DD)')
                                         .required('Please enter a starting date of loan')
                                         .max(new Date(), "This date can't be in the future!"),
                    })}
                >   
                    <FormGroup>
                        <Label for="firstName">First Name</Label>
                        <Input name="firstName" />
                    </FormGroup>
 
                    <FormGroup>
                        <Label for="lastName">Last Name</Label>
                        <Input name="lastName" />
                    </FormGroup>

                    <DateField
                        label="Borrowed From"
                        id="borrowedFrom"
                        name="borrowedFrom"
                        displayFormat={() => "DD.MM.YYYY"}
                        max={{ value: 0, units: 'day' }}
                    />

                    <FormGroup>
                        <Button type="submit" className="ml-1" color="primary">
                            Submit
                        </Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
        )
    }

    render() {
        const {item} = this.state;
        let dialog = this.props.params.action === 'loan' ?
                        this.loanDialog(item) : this.addOrEditDialog(item);

        return dialog;
    }
}

export default withParams(BookEdit);