import React from 'react';
import styles from './Navbar.module.css';
import logo from '../../assets/images/logo-softcloset-v2.png'
import Container from '../Container/Container';

export default function Navbar({}) {
  return (
    <nav>
      <Container>
        <div className={styles.navbar}>
          <img className={styles.logoNavbar} src={logo} alt="logo SoftCloset" />
          
        </div>
      </Container>
    </nav>
  );
}