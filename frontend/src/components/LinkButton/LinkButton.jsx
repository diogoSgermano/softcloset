import { Link } from 'react-router-dom';
import styles from './LinkButton.module.css';

export default function LinkButton({ to, children, variant, className }) {
    return (
        <Link to={to} className={`${styles.btn} ${styles[variant] || ''} ${className || ''}`}>
            {children}
        </Link>
    );
}