/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.enums;

/**
 * The Enum Vars.
 */
public enum Vars {

    /** The proof template sign. */
    PROOF_TEMPLATE_SIGN("VerifSignPreuveTemplate.xml"),
    /** The proof template cert. */
    PROOF_TEMPLATE_CERT("VerifCertPreuveTemplate.xml"),

    /** The confdir. */
    CONFDIR("conf/"),
    /** The proof logtext. */
    PROOF_LOGTEXT("Preuve:\n{}"),
    /** The metadata logtext. */
    METADATA_LOGTEXT("Metadata:{}"),

    /** The docsign logtext. */
    DOCSIGN_LOGTEXT("Document signé:\n{}"),
    /** The certusages logtext. */
    CERTUSAGES_LOGTEXT("Usages du certificat:{}"),

    /** The rules not loaded. */
    RULES_NOT_LOADED("Des règles sont manquantes dans le fichier de configuration."
            + " Vérifiez que toutes les règles sont présentes."),

    /** The unwkown exception message. */
    UNWKOWN_EXCEPTION_MESSAGE("Une erreur inconnue s'est produite. Contactez votre administrateur."),

    /** The client exception message. */
    CLIENT_EXCEPTION_MESSAGE("L'opération sur le document soumis est impossible."),

    /** The server exception message. */
    SERVER_EXCEPTION_MESSAGE("Une erreur interne du serveur s'est produite."),

    /** The preuve conf message. */
    PREUVE_CONF_MESSAGE("Le fichier de configuration de la preuve n'a pas pu être chargé."),

    /** The validation signature conf message. */
    VALIDATION_SIGNATURE_CONF_MESSAGE("Le fichier de configuration de la validation de signature"
            + " (règles et metadata) n'a pas pu être chargé."),

    /** The signature conf message. */
    SIGNATURE_CONF_MESSAGE("Le fichier de configuration de la signature n'a pas pu être chargé."),

    /** The certificat conf message. */
    CERTIFICAT_CONF_MESSAGE("Le fichier de configuration de la validation de certificat" + " n'a pas pu être chargé."),

    /** The certificat sign message. */
    CERTIFICAT_SIGN_MESSAGE("Le certificat et/ou le password du certificat de signature"
            + " ne permet pas de signer le document ou la preuve."),

    /** The transform algorithm message. */
    TRANSFORM_ALGORITHM_MESSAGE("L'algorithme de transformation spécifié est invalide."),

    /** The canon algorithm message. */
    CANON_ALGORITHM_MESSAGE("L'algorithme de canonisation spécifié est invalide."),

    /** The digest algorithm message. */
    DIGEST_ALGORITHM_MESSAGE("L'algorithme de chiffrement spécifié est invalide.");

    /** The var. */
    private final String var;

    /**
     * Instantiates a new vars.
     *
     * @param var the var
     */
    private Vars(String var) {
        this.var = var;
    }

    /**
     * Gets the var.
     *
     * @return the var
     */
    public String getVar() {
        return var;
    }
}
