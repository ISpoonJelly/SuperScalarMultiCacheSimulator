package Superscalar;

import cache.CacheHandler;

public class Brain {
	public int cycles = 0;
	public SuperScalar superScalar;
	public ROB rob;

	public Brain(int sn, RegisterFile registerFile, CacheHandler cHandler,
			WriteCycles writeCycles, ExecuteCycles execCycles, ROB rob,
			ScoreBoard scoreBoard, RegisterStatus registerStatus) {
		superScalar = new SuperScalar(sn, registerFile, cHandler, writeCycles,
				execCycles, rob, scoreBoard, registerStatus);
		this.rob = rob;
	}

	public boolean simulate() {
		if (cycles == 0) {
			ScoreBoard tempScoreBoard = copyScoreBoard(SuperScalar.scoreboard);
			ROB tempROB = copyROB(SuperScalar.rob);
			System.out.println("Cycles: " + cycles);
			System.out.println("----------------");
			System.out.println("Original ScoreBoard");
			System.out.println(SuperScalar.scoreboard);
			System.out.println("------------------");
			System.out.println("Original ROB");
			System.out.println(SuperScalar.rob);

			superScalar.commit();
			superScalar.write();
			ScoreBoard commitWriteScoreBoard = copyScoreBoard(SuperScalar.scoreboard);
			ROB commitWriteROB = copyROB(SuperScalar.rob);

			System.out.println("----------------");
			System.out.println("Commit Write ScoreBoard");
			System.out.println(SuperScalar.scoreboard);
			System.out.println("------------------");
			System.out.println("Commit Write ROB");
			System.out.println(SuperScalar.rob);
			System.out.println("-----------------");

			SuperScalar.scoreboard = copyScoreBoard(tempScoreBoard);
			SuperScalar.rob = copyROB(tempROB);
			superScalar.execute();
			superScalar.fetch();
			superScalar.issue();
			// TODO: Combine the 2 ROBs and 2 ScoreBoards
			ScoreBoard finalscoreBoard = combineScoreBoards(
					SuperScalar.scoreboard, commitWriteScoreBoard,
					tempScoreBoard);
			ROB finalROB = combineROB(SuperScalar.rob, commitWriteROB, tempROB);
			System.out.println("Issue Execute ScoreBoard");
			System.out.println(SuperScalar.scoreboard);
			System.out.println("------------------");
			System.out.println("Issue Execute ROB");
			System.out.println(SuperScalar.rob);
			System.out.println("-----------------");

			SuperScalar.scoreboard = copyScoreBoard(finalscoreBoard);
			SuperScalar.rob = copyROB(finalROB);

			System.out.println("Final  ScoreBoard");
			System.out.println(SuperScalar.scoreboard);
			System.out.println("------------------");
			System.out.println("Final  ROB");
			System.out.println(SuperScalar.rob);
			System.out.println("-----------------");
			System.out.println("--- Final Head ----");
			System.out.println(SuperScalar.rob.getHead());
			System.out.println("--- Final Tail ----");
			System.out.println(SuperScalar.rob.getTail());
			cycles++;
			return true;

		}
		if (SuperScalar.rob.isEmpty() && SuperScalar.issueReg.isEmpty()
				&& SuperScalar.executeReg.isEmpty()
				&& SuperScalar.writeReg.isEmpty()
				&& SuperScalar.commitReg.isEmpty()){
			System.out.println("DONE");
			return false;
		}
			System.out.println(SuperScalar.rob.isEmpty() + " ROB EMPTY");
		if (!SuperScalar.rob.isEmpty()) {
			System.out.println(SuperScalar.rob.isEmpty() + " ROB EMPTY");
			System.out.println("Cycles: " + cycles);

			ScoreBoard tempScoreBoard = copyScoreBoard(SuperScalar.scoreboard);
			ROB tempROB = copyROB(SuperScalar.rob);

			System.out.println("----------------");
			System.out.println("Original ScoreBoard");
			System.out.println(tempScoreBoard);
			System.out.println("------------------");
			System.out.println("Original ROB");
			System.out.println(tempROB);

			superScalar.commit();
			superScalar.write();

			ScoreBoard commitWriteScoreBoard = copyScoreBoard(SuperScalar.scoreboard);
			ROB commitWriteROB = copyROB(SuperScalar.rob);
			SuperScalar.scoreboard = copyScoreBoard(tempScoreBoard);
			SuperScalar.rob = copyROB(tempROB);
			System.out.println("----------------");
			System.out.println("Commit Write ScoreBoard");
			System.out.println(commitWriteScoreBoard);
			System.out.println("------------------");
			System.out.println("Commit Write ROB");
			System.out.println(commitWriteROB);
			System.out.println("-----------------");

			superScalar.execute();
			superScalar.fetch();
			superScalar.issue();
			// Combine the 2 ROBs and 2 ScoreBoards
			ScoreBoard finalscoreBoard = combineScoreBoards(
					SuperScalar.scoreboard, commitWriteScoreBoard,
					tempScoreBoard);
			ROB finalROB = combineROB(SuperScalar.rob, commitWriteROB, tempROB);
			System.out.println("Issue Execute ScoreBoard");
			System.out.println(SuperScalar.scoreboard);
			System.out.println("------------------");
			System.out.println("Issue Execute ROB");
			System.out.println(SuperScalar.rob);
			System.out.println("-----------------");

			SuperScalar.scoreboard = copyScoreBoard(finalscoreBoard);
			SuperScalar.rob = copyROB(finalROB);

			System.out.println("Final  ScoreBoard");
			System.out.println(finalscoreBoard);
			System.out.println("------------------");
			System.out.println("Final  ROB");
			System.out.println(finalROB);
			System.out.println("-----------------");
			System.out.println("--- Final Head ----");
			System.out.println(SuperScalar.rob.getHead());
			System.out.println("--- Final Tail ----");
			System.out.println(SuperScalar.rob.getTail());
			cycles++;
			return true;
		}

		if (!SuperScalar.issueReg.isEmpty()) {
			System.out.println("NOT NO MORE INSTRUCTIONS");
			ScoreBoard tempScoreBoard = copyScoreBoard(SuperScalar.scoreboard);
			ROB tempROB = copyROB(SuperScalar.rob);

			System.out.println("Cycles: " + cycles);
			System.out.println("----------------");
			System.out.println("Original ScoreBoard");
			System.out.println(tempScoreBoard);
			System.out.println("------------------");
			System.out.println("Original ROB");
			System.out.println(tempROB);
			System.out.println("==-=-=-=--------=-=-=-=-");
			System.out.println(SuperScalar.commitReg);
			System.out.println("--------Write-------");
			System.out.println(SuperScalar.writeReg);
			superScalar.commit();

			superScalar.write();

			ScoreBoard commitWriteScoreBoard = copyScoreBoard(SuperScalar.scoreboard);
			ROB commitWriteROB = copyROB(SuperScalar.rob);
			System.out.println("Cycles: " + cycles);
			System.out.println("----------------");
			System.out.println("Commit Write ScoreBoard");
			System.out.println(commitWriteScoreBoard);
			System.out.println("------------------");
			System.out.println("Commit Write ROB");
			System.out.println(commitWriteROB);
			System.out.println("-----------------");

			SuperScalar.scoreboard = copyScoreBoard(tempScoreBoard);
			SuperScalar.rob = copyROB(tempROB);
			superScalar.execute();
			superScalar.fetch();
			superScalar.issue();
			// Combine the 2 ROBs and 2 ScoreBoards
			ScoreBoard finalscoreBoard = combineScoreBoards(
					SuperScalar.scoreboard, commitWriteScoreBoard,
					tempScoreBoard);
			ROB finalROB = combineROB(SuperScalar.rob, commitWriteROB, tempROB);
			System.out.println("Issue Execute ScoreBoard");
			System.out.println(SuperScalar.scoreboard);
			System.out.println("------------------");
			System.out.println("Issue Execute ROB");
			System.out.println(SuperScalar.rob);
			System.out.println("-----------------");

			SuperScalar.scoreboard = copyScoreBoard(finalscoreBoard);
			SuperScalar.rob = copyROB(finalROB);

			System.out.println("Final  ScoreBoard");
			System.out.println(finalscoreBoard);
			System.out.println("------------------");
			System.out.println("Final  ROB");
			System.out.println(finalROB);
			System.out.println("-----------------");
			System.out.println("--- Final Head ----");
			System.out.println(SuperScalar.rob.getHead());
			System.out.println("--- Final Tail ----");
			System.out.println(SuperScalar.rob.getTail());
			cycles++;
			return true;
		}

		return false;
	}

	private ROB combineROB(ROB finalrob, ROB commitWriteROB, ROB originalROB) {

		for (int i = 1; i < originalROB.getROBEntry().length; i++) {
		//	System.out.println(finalrob.getROBEntry()[i] + " Here " + i);
			if (originalROB.getROBEntry()[i] == null
					&& finalrob.getROBEntry()[i] != null) {
				//System.out.println("HEREE FULL " + finalrob.getROBEntry()[i]);
				originalROB.getROBEntry()[i] = finalrob.getROBEntry()[i];
				originalROB.setTail(finalrob.getTail());
			} else {
				if (originalROB.getROBEntry()[i] != null
						&& commitWriteROB.getROBEntry()[i] == null) {
					originalROB.getROBEntry()[i] = null;
					originalROB.setHead(commitWriteROB.getHead());
				} else {
					if (originalROB.getROBEntry()[i] != null
							&& !originalROB.getROBEntry()[i].isReady()
							&& commitWriteROB.getROBEntry()[i] != null
							&& commitWriteROB.getROBEntry()[i].isReady()) {
						originalROB.getROBEntry()[i] = commitWriteROB
								.getROBEntry()[i];
					}
				}
			}
		}
		return originalROB;
	}

	private ScoreBoard combineScoreBoards(ScoreBoard finalscoreboard,
			ScoreBoard commitWriteScoreBoard, ScoreBoard originalScoreBoard) {

		for (int i = 1; i <= originalScoreBoard.getLoad(); i++) {
			String l = "load" + i;
			if (originalScoreBoard.getScoreBoard().get(l).isBusy()) {
				if (!commitWriteScoreBoard.getScoreBoard().get(l).isBusy())
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (!originalScoreBoard.getScoreBoard().get(l).isBusy()) {
					if (finalscoreboard.getScoreBoard().get(l).isBusy()) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// store reservation stations
		for (int i = 1; i <= originalScoreBoard.getStore(); i++) {
			String l = "store" + i;
			if (originalScoreBoard.getScoreBoard().get(l).isBusy()) {
				if (!commitWriteScoreBoard.getScoreBoard().get(l).isBusy())
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (!originalScoreBoard.getScoreBoard().get(l).isBusy()) {
					if (finalscoreboard.getScoreBoard().get(l).isBusy()) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// add reservation stations
		for (int i = 1; i <= originalScoreBoard.getAdd(); i++) {
			String l = "add" + i;
			if (originalScoreBoard.getScoreBoard().get(l).isBusy()) {
				if (!commitWriteScoreBoard.getScoreBoard().get(l).isBusy())
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (!originalScoreBoard.getScoreBoard().get(l).isBusy()) {
					if (finalscoreboard.getScoreBoard().get(l).isBusy()) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// mult reservation stations
		for (int i = 1; i <= originalScoreBoard.getMult(); i++) {
			String l = "mult" + i;
			if (originalScoreBoard.getScoreBoard().get(l).isBusy()) {
				if (!commitWriteScoreBoard.getScoreBoard().get(l).isBusy())
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (!originalScoreBoard.getScoreBoard().get(l).isBusy()) {
					if (finalscoreboard.getScoreBoard().get(l).isBusy()) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// jump reservation stations
		for (int i = 1; i <= originalScoreBoard.getJump(); i++) {
			String l = "jump" + i;
			if (originalScoreBoard.getScoreBoard().get(l).isBusy()) {
				if (!commitWriteScoreBoard.getScoreBoard().get(l).isBusy())
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (!originalScoreBoard.getScoreBoard().get(l).isBusy()) {
					if (finalscoreboard.getScoreBoard().get(l).isBusy()) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// return reservation stations
		for (int i = 1; i <= originalScoreBoard.getRet(); i++) {
			String l = "ret" + i;
			if (originalScoreBoard.getScoreBoard().get(l).isBusy()) {
				if (!commitWriteScoreBoard.getScoreBoard().get(l).isBusy())
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (!originalScoreBoard.getScoreBoard().get(l).isBusy()) {
					if (finalscoreboard.getScoreBoard().get(l).isBusy()) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}
		// nand reservation stations
		for (int i = 1; i <= originalScoreBoard.getNand(); i++) {
			String l = "nand" + i;
			if (originalScoreBoard.getScoreBoard().get(l).isBusy()) {
				if (!commitWriteScoreBoard.getScoreBoard().get(l).isBusy())
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (!originalScoreBoard.getScoreBoard().get(l).isBusy()) {
					if (finalscoreboard.getScoreBoard().get(l).isBusy()) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		return originalScoreBoard;
	}

	public ScoreBoard copyScoreBoard(ScoreBoard original) {
		ScoreBoard copy = new ScoreBoard(SuperScalar.scoreboard.getLoad(),
				SuperScalar.scoreboard.getStore(),
				SuperScalar.scoreboard.getAdd(),
				SuperScalar.scoreboard.getMult(),
				SuperScalar.scoreboard.getJump(),
				SuperScalar.scoreboard.getRet(),
				SuperScalar.scoreboard.getNand());

		// load reservation stations
		for (int i = 1; i <= original.getLoad(); i++) {
			String l = "load" + i;
			ScoreBoardEntry e = SuperScalar.scoreboard.getScoreBoard().get(l);
			ScoreBoardEntry entry = new ScoreBoardEntry(e.isBusy(),
					e.getOperation(), e.getVj(), e.getVk(), e.getQj(),
					e.getQk(), e.getDestination(), e.getA());
			copy.getScoreBoard().put(l, entry);
		}

		// store reservation stations
		for (int i = 1; i <= original.getStore(); i++) {
			String l = "store" + i;
			ScoreBoardEntry e = SuperScalar.scoreboard.getScoreBoard().get(l);
			ScoreBoardEntry entry = new ScoreBoardEntry(e.isBusy(),
					e.getOperation(), e.getVj(), e.getVk(), e.getQj(),
					e.getQk(), e.getDestination(), e.getA());
			copy.getScoreBoard().put(l, entry);
		}

		// add reservation stations
		for (int i = 1; i <= original.getAdd(); i++) {
			String l = "add" + i;
			ScoreBoardEntry e = SuperScalar.scoreboard.getScoreBoard().get(l);
			ScoreBoardEntry entry = new ScoreBoardEntry(e.isBusy(),
					e.getOperation(), e.getVj(), e.getVk(), e.getQj(),
					e.getQk(), e.getDestination(), e.getA());
			copy.getScoreBoard().put(l, entry);
		}

		for (int i = 1; i <= original.getMult(); i++) {
			String l = "mult" + i;
			ScoreBoardEntry e = SuperScalar.scoreboard.getScoreBoard().get(l);
			ScoreBoardEntry entry = new ScoreBoardEntry(e.isBusy(),
					e.getOperation(), e.getVj(), e.getVk(), e.getQj(),
					e.getQk(), e.getDestination(), e.getA());
			copy.getScoreBoard().put(l, entry);
		}

		// jump reservation stations
		for (int i = 1; i <= original.getJump(); i++) {
			String l = "jump" + i;
			ScoreBoardEntry e = SuperScalar.scoreboard.getScoreBoard().get(l);
			ScoreBoardEntry entry = new ScoreBoardEntry(e.isBusy(),
					e.getOperation(), e.getVj(), e.getVk(), e.getQj(),
					e.getQk(), e.getDestination(), e.getA());
			copy.getScoreBoard().put(l, entry);
		}

		// return reservation stations
		for (int i = 1; i <= original.getRet(); i++) {
			String l = "ret" + i;
			ScoreBoardEntry e = SuperScalar.scoreboard.getScoreBoard().get(l);
			ScoreBoardEntry entry = new ScoreBoardEntry(e.isBusy(),
					e.getOperation(), e.getVj(), e.getVk(), e.getQj(),
					e.getQk(), e.getDestination(), e.getA());
			copy.getScoreBoard().put(l, entry);
		}
		// nand reservation stations
		for (int i = 1; i <= original.getNand(); i++) {
			String l = "nand" + i;
			ScoreBoardEntry e = SuperScalar.scoreboard.getScoreBoard().get(l);
			ScoreBoardEntry entry = new ScoreBoardEntry(e.isBusy(),
					e.getOperation(), e.getVj(), e.getVk(), e.getQj(),
					e.getQk(), e.getDestination(), e.getA());
			copy.getScoreBoard().put(l, entry);
		}
		return copy;
	}

	public ROB copyROB(ROB original) {
		ROB copy = new ROB(original.size);
		copy.setHead(original.getHead());
		copy.setTail(original.getTail());
		
		for (int i = 1; i < original.getROBEntry().length; i++) {
			if (original.getROBEntry()[i] == null) {
				copy.getROBEntry()[i] = null;
			} else {
				ROBEntry e = original.getROBEntry()[i];
				copy.getROBEntry()[i] = new ROBEntry(e.getType(), e.getDest(),
						e.getValue(), e.isReady());
			}
		}
		
		
		
		System.out.println(copy.getHead() + " HEAD");
		System.out.println(copy.getTail() + " TAIL");
		return copy;
	}

}
