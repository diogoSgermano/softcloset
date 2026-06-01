import React from 'react';
import Navbar from '../../layouts/Navbar/Navbar';
import Footer from '../../layouts/Footer/Footer';
import img_background from '../../assets/images/pagina-inicial-background.jpg';
import styles from './Home.module.css';
import Destaques from '../../components/Destaques/Destaques';

export default function Home() {
  return (
    <>
      <Navbar />
      <main>
          <section className={styles.section}>
            <h1>ESTILO QUE VESTE VOCÊ</h1>
            <h2>Encontre as melhores peças para todas as ocasiões</h2>
            <img className={styles.img} src={img_background} alt="foto introdutória" />
          </section>
          <section>
            <Destaques />
          </section>
      </main>
      <Footer />
    </>
  );
}
