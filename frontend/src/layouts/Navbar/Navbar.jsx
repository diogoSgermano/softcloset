import React from 'react';
import styles from './Navbar.module.css';
import logo from '../../assets/images/logo-softcloset-v2.png'
import Container from '../Container/Container';
import LinkButton from '../../components/LinkButton/LinkButton.jsx'

export default function Navbar() {
  return (
    <nav>
        <div className={styles.navbar}>
          <div className={styles.logoContainer}>
            <LinkButton to="/" variant="transparent">
              <img className={styles.logoNavbar} src={logo} alt="logo SoftCloset" />
            </LinkButton>
          </div>

          <ul className={styles.navLinks}>
            <li>
                <LinkButton to="/masculino" variant="transparent">Masculino</LinkButton>
            </li>
            <li>
              <LinkButton to="/feminino" variant="transparent">Feminino</LinkButton>
            </li>
          </ul>

          <div className={styles.spacer}></div>
        </div>

    </nav>
  );
}