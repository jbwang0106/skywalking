/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.collector.agent.stream.worker.jvm;

import org.skywalking.apm.collector.queue.service.QueueCreatorService;
import org.skywalking.apm.collector.storage.base.dao.IPersistenceDAO;
import org.skywalking.apm.collector.storage.dao.ICpuMetricStreamDAO;
import org.skywalking.apm.collector.storage.service.DAOService;
import org.skywalking.apm.collector.storage.table.jvm.CpuMetric;
import org.skywalking.apm.collector.stream.worker.base.AbstractLocalAsyncWorkerProvider;
import org.skywalking.apm.collector.stream.worker.impl.PersistenceWorker;

/**
 * @author peng-yongsheng
 */
public class CpuMetricPersistenceWorker extends PersistenceWorker<CpuMetric, CpuMetric> {

    private final DAOService daoService;

    @Override public int id() {
        return 0;
    }

    public CpuMetricPersistenceWorker(DAOService daoService) {
        super(daoService);
        this.daoService = daoService;
    }

    @Override protected boolean needMergeDBData() {
        return false;
    }

    @Override protected IPersistenceDAO persistenceDAO() {
        return daoService.getPersistenceDAO(ICpuMetricStreamDAO.class);
    }

    public static class Factory extends AbstractLocalAsyncWorkerProvider<CpuMetricPersistenceWorker> {

        public Factory(DAOService daoService, QueueCreatorService queueCreatorService) {
            super(daoService, queueCreatorService);
        }

        @Override
        public CpuMetricPersistenceWorker workerInstance(DAOService daoService) {
            return new CpuMetricPersistenceWorker(daoService);
        }

        @Override
        public int queueSize() {
            return 1024;
        }
    }
}
