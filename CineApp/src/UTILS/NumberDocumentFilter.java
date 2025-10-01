package UTILS;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * es un filtro de documento que restringe la entrada de texto
 * a solo dígitos numericos y limita la cantidad de dígitos que se pueden ingresar.
 *
 * <p>Este filtro se utiliza con {@code JFormattedTextField} para asegurar que solo se ingresen
 * numeros y para controlar la longitud maxima de la entrada.</p>
 *
 * @author alons
 */
public class NumberDocumentFilter extends DocumentFilter {

    private int maxDigits;

    /**
     * Crea un nuevo con la cantidad máxima de dígitos permitidos.
     *
     * @param maxDigits La cantidad maxima de digitos que se pueden ingresar.
     */
    public NumberDocumentFilter(int maxDigits) {
        this.maxDigits = maxDigits;
    }

    /**
     * Inserta una cadena de texto en el documento, verificando que la cadena sea numerica
     * y que la longitud del texto resultante no exceda la cantidad maxima de dígitos permitidos.
     *
     * @param fb     Un objeto que se puede utilizar para evitar el filtro.
     * @param offset La posicion en el documento donde se va a insertar el texto.
     * @param string La cadena de texto que se va a insertar.
     * @param attr   El conjunto de atributos que se van a asociar con el texto insertado.
     * @throws BadLocationException Si la posicion especificada no es valida dentro del documento.
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (isNumeric(string) && fb.getDocument().getLength() + string.length() <= maxDigits) {
            super.insertString(fb, offset, string, attr);
        }
    }

    /**
     * Reemplaza una porción de texto en el documento, verificando que la cadena de reemplazo
     * sea numerica y que la longitud del texto resultante no exceda la cantidad maxima de
     * digitos permitidos.
     *
     * @param fb     Un objeto que se puede utilizar para evitar el filtro.
     * @param offset La posición en el documento donde se va a reemplazar el texto.
     * @param length La cantidad de texto que se va a reemplazar.
     * @param text   La cadena de texto que se va a utilizar como reemplazo.
     * @param attrs  El conjunto de atributos que se van a asociar con el texto reemplazado.
     * @throws BadLocationException Si la posicion especificada no es valida dentro del documento.
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.isEmpty() || (isNumeric(text) && fb.getDocument().getLength() - length + text.length() <= maxDigits)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    /**
     * Verifica si una cadena de texto contiene solo dígitos numericos.
     *
     * @param text La cadena de texto que se va a verificar.
     * @return {@code true} si la cadena contiene solo dígitos numericos
     */
    private boolean isNumeric(String text) {
        return text == null || text.isEmpty() || text.matches("\\d+");
    }
}
