﻿# DS-SIM Scheduling Client
## Scheduling Client and Algorithm
ds-sim is a discrete-event simulator that has been developed primarily for leveraging scheduling algorithm design. It adopts a minimalist design explicitly taking into account modularity in that it uses the client-server model. This client-side simulator for ds-sim acts as a job scheduler while the server-side simulator simulates everything else including users (job submissions) and servers (job execution).

---

## How to run simulation
1. run ds-server from ds-sim source '$ ./ds-server [-c config_path] [-n]'
2. run this client '$ java MyClient'
