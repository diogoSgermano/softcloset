import React from 'react';
import Navbar from '../../layouts/Navbar/Navbar';
import Footer from '../../layouts/Footer/Footer';
import Container from '../../layouts/Container/Container';

export default function Home() {
  return (
    <>
      <Navbar />
      <Container>
        <h1>Home</h1>
      </Container>
      <Footer />
    </>
  );
}
