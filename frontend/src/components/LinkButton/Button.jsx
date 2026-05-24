import {Link} from 'react-router-dom'
import styles from './Button.module.css'
export default function Button ({text,variant,onClick}){
    return(
        <button className={`${styles.btn} ${styles[variant]}`} onClick={onClick}>
            {text}
        </button>
    );
}
    
