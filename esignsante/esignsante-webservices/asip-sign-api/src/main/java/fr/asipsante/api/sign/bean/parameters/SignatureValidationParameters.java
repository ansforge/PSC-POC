/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.parameters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;

/**
 * La classe Rules.
 */
public class SignatureValidationParameters {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureValidationParameters.class);

    /** The description. */
    private String description;

    /** The rules. */
    private List<IVisitor> rules;

    /** The meta data. */
    private List<MetaDatum> metaData;

    /**
     * Instantiates a new signature validation parameters.
     */
    public SignatureValidationParameters() {
        rules = new ArrayList<>();
        metaData = new ArrayList<>();
    }

    /**
     * Instantiates a new signature validation parameters.
     *
     * @param rules    the rules
     * @param metaData the meta data
     */
    public SignatureValidationParameters(List<IVisitor> rules, List<MetaDatum> metaData) {
        this.rules = rules;
        this.metaData = metaData;
    }

    /**
     * Gets the rules.
     *
     * @return the rules
     */
    public List<IVisitor> getRules() {
        return rules;
    }

    /**
     * Sets the rules.
     *
     * @param signRules the new rules
     */
    public void setRules(List<IVisitor> signRules) {
        this.rules = signRules;
    }

    /**
     * Gets the meta data.
     *
     * @return the meta data
     */
    public List<MetaDatum> getMetaData() {
        return metaData;
    }

    /**
     * Sets the meta data.
     *
     * @param metaData the new meta data
     */
    public void setMetaData(List<MetaDatum> metaData) {
        this.metaData = metaData;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Méthode permettant de charger l'objet de classe Rules à l'aide d'une liste de
     * règles.
     *
     * @param rulesNames the rules names
     */
    public void loadRules(List<String> rulesNames) {

        final String strPackage = "fr.asipsante.api.sign.validation.signature.rules.impl";

        for (final String rule : rulesNames) {
            try {
                rules.add((IVisitor) Class.forName(strPackage + "." + rule).newInstance());
            } catch (final ReflectiveOperationException e) {
                LOG.warn("{} n'est pas reconnue comme règle de validation de signature.", rule);
            }
        }
    }

    /**
     * Load metadata.
     *
     * @param metaData the meta data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void loadMetadata(List<String> metaData) throws IOException {

        final MetaDatum metaDateSign = new MetaDatum(MetaDataType.DATE_SIGNATURE, "");
        final MetaDatum metaDNCert = new MetaDatum(MetaDataType.DN_CERTIFICAT, "");
        final MetaDatum metaRapportDiagnostique = new MetaDatum(MetaDataType.RAPPORT_DIAGNOSTIQUE, "");
        final MetaDatum metaDocOrigin = new MetaDatum(MetaDataType.DOCUMENT_ORIGINAL_NON_SIGNE, "");
        final MetaDatum metaRapportDSS = new MetaDatum(MetaDataType.RAPPORT_DSS, "");
        
        for (final String meta : metaData) {
            if (meta.equals(MetaDataType.DATE_SIGNATURE.toString())) {
                this.metaData.add(metaDateSign);
            } else if (meta.equals(MetaDataType.DN_CERTIFICAT.toString())) {
                this.metaData.add(metaDNCert);
            } else if (meta.equals(MetaDataType.RAPPORT_DIAGNOSTIQUE.toString())) {
                this.metaData.add(metaRapportDiagnostique);
            } else if (meta.equals(MetaDataType.DOCUMENT_ORIGINAL_NON_SIGNE.toString())) {
                this.metaData.add(metaDocOrigin);
            } else if (meta.equals(MetaDataType.RAPPORT_DSS.toString())) {
                this.metaData.add(metaRapportDSS);
            }
        }
    }

    /**
     * Gets the metadat as string.
     *
     * @return the metadat as string
     */
    public String getMetadatAsString() {
        final StringBuilder bld = new StringBuilder();
        if (metaData.isEmpty()) {
            bld.append(" Pas de metadata à retourner.");
        } else {
            for (final MetaDatum metadatum : metaData) {
                bld.append("\n");
                bld.append(metadatum.toString());
            }
        }
        return bld.toString();
    }

}
