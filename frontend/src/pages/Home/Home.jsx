import React from 'react';
import Navbar from '../../layouts/Navbar/Navbar';
import Footer from '../../layouts/Footer/Footer';
import Container from '../../layouts/Container/Container';
import img_background from '../../assets/images/pagina-inicial-background.jpg'
import styles from './Home.module.css'
export default function Home() {
  return (
    <>
      <Navbar />
      <Container>
        <section className={styles.section}>
            <h1>ESTILO QUE VESTE VOCÊ</h1>
            <h3>Encontre as melhores peças para todas as ocasiões</h3>
            <img className={styles.img} src={img_background} alt="foto introdutória"></img>
        </section>
      </Container>
      <Footer />
    </>
  );
}
