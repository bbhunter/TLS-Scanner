/**
 * TLS-Scanner - A TLS Configuration Analysistool based on TLS-Attacker
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsscanner.probe;

import de.rub.nds.tlsattacker.attacks.config.PaddingOracleCommandConfig;
import de.rub.nds.tlsattacker.attacks.config.PoodleCommandConfig;
import de.rub.nds.tlsattacker.attacks.config.TLSPoodleCommandConfig;
import de.rub.nds.tlsattacker.attacks.impl.PaddingOracleAttacker;
import de.rub.nds.tlsattacker.attacks.impl.PoodleAttacker;
import de.rub.nds.tlsattacker.attacks.impl.TLSPoodleAttacker;
import de.rub.nds.tlsattacker.core.config.delegate.ClientDelegate;
import de.rub.nds.tlsscanner.config.ScannerConfig;
import de.rub.nds.tlsscanner.report.ProbeResult;
import de.rub.nds.tlsscanner.report.ResultValue;
import de.rub.nds.tlsscanner.report.check.CheckType;
import de.rub.nds.tlsscanner.report.check.TLSCheck;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class TlsPoodleProbe extends TLSProbe {

    public TlsPoodleProbe(ScannerConfig config) {
        super(ProbeType.TLS_POODLE, config);
    }

    @Override
    public ProbeResult call() {
        LOGGER.debug("Starting TLS-Poodle Probe");
        TLSPoodleCommandConfig poodleCommandConfig = new TLSPoodleCommandConfig(getScannerConfig().getGeneralDelegate());
        ClientDelegate delegate = (ClientDelegate) poodleCommandConfig.getDelegate(ClientDelegate.class);
        delegate.setHost(getScannerConfig().getClientDelegate().getHost());
        TLSPoodleAttacker attacker = new TLSPoodleAttacker(poodleCommandConfig);
        Boolean vulnerable = attacker.isVulnerable();
        TLSCheck check = new TLSCheck(vulnerable, CheckType.ATTACK_TLS_POODLE, 10);
        List<TLSCheck> checkList = new LinkedList<>();
        checkList.add(check);
        return new ProbeResult(getType(), new LinkedList<ResultValue>(), checkList);
    }

}