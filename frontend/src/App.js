import AllBooksList from './AllBooksList';
import AvailableBooksList from './AvailableBooksList';
import LoanedBooksList from './LoanedBooksList';
import BookEdit from "./BookEdit";

import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

function App() {
  return (
    <Router>
      <nav style={{ margin: 10 }}>
          <Link to="/books" style={{ padding: 5 }}>
          All Books
          </Link>
          <Link to="/available-books" style={{ padding: 5 }}>
          Available Books
          </Link>
          <Link to="/loaned-books" style={{ padding: 5 }}>
          Loaned Books
          </Link>
      </nav>
      <Routes>
        <Route path="/books" element={<AllBooksList/>} />
        <Route path="/loaned-books" element={<LoanedBooksList/>} />
        <Route path="/available-books" element={<AvailableBooksList/>} />
        <Route path='/books/:id' element={<BookEdit/>} />
        <Route path='/books/:action/:id' element={<BookEdit/>} />
        <Route path="*" element={<AllBooksList />} />
      </Routes>
    </Router>
  );
}

export default App;