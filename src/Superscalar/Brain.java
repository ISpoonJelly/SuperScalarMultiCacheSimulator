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
			ScoreBoard tempScoreBoard = SuperScalar.scoreboard;
			ROB tempROB = SuperScalar.rob;
			superScalar.commit();
			superScalar.write();
			ScoreBoard commitWriteScoreBoard = SuperScalar.scoreboard;
			ROB commitWriteROB = SuperScalar.rob;
			SuperScalar.scoreboard = tempScoreBoard;
			SuperScalar.rob = tempROB;
			superScalar.execute();
			// fetch first
			superScalar.issue();
			// TODO: Combine the 2 ROBs and 2 ScoreBoards
			ScoreBoard finalscoreBoard = combineScoreBoards(
					SuperScalar.scoreboard, commitWriteScoreBoard,
					tempScoreBoard);
			ROB finalROB = combineROB(SuperScalar.rob, commitWriteROB, tempROB);
			superScalar.scoreboard = finalscoreBoard;
			superScalar.rob = finalROB;
			cycles++;
			return true;

		} else {
			if (!rob.isEmpty()) {
				ScoreBoard tempScoreBoard = SuperScalar.scoreboard;
				ROB tempROB = SuperScalar.rob;
				superScalar.commit();
				superScalar.write();
				ScoreBoard commitWriteScoreBoard = SuperScalar.scoreboard;
				ROB commitWriteROB = SuperScalar.rob;
				SuperScalar.scoreboard = tempScoreBoard;
				SuperScalar.rob = tempROB;
				superScalar.execute();
				// fetch first
				superScalar.issue();
				// Combine the 2 ROBs and 2 ScoreBoards
				ScoreBoard finalscoreBoard = combineScoreBoards(
						SuperScalar.scoreboard, commitWriteScoreBoard,
						tempScoreBoard);
				ROB finalROB = combineROB(SuperScalar.rob, commitWriteROB,
						tempROB);
				superScalar.scoreboard = finalscoreBoard;
				superScalar.rob = finalROB;
				cycles++;
				return true;
			}

		}
		return false;
	}

	private ROB combineROB(ROB finalrob, ROB commitWriteROB, ROB originalROB) {
		for (int i = 1; i < originalROB.size; i++) {
			if (originalROB.getROBEntry()[i] == null
					&& finalrob.getROBEntry()[i] != null) {
				originalROB.setROBEntry(finalrob.getROBEntry()[i], i);
			} else {
				if (originalROB.getROBEntry()[i] != null
						&& commitWriteROB.getROBEntry()[i] == null) {
					originalROB.setROBEntry(null, i);
				}
			}
		}
		return originalROB;
	}

	private ScoreBoard combineScoreBoards(ScoreBoard finalscoreboard,
			ScoreBoard commitWriteScoreBoard, ScoreBoard originalScoreBoard) {

		for (int i = 1; i <= originalScoreBoard.getLoad(); i++) {
			String l = "load" + i;
			if (originalScoreBoard.getScoreBoard().get(l).getOperation() != null) {
				if (commitWriteScoreBoard.getScoreBoard().get(l).getOperation() == null)
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (originalScoreBoard.getScoreBoard().get(l).getOperation() == null) {
					if (finalscoreboard.getScoreBoard().get(l).getOperation() != null) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// store reservation stations
		for (int i = 1; i <= originalScoreBoard.getStore(); i++) {
			String l = "store" + i;
			if (originalScoreBoard.getScoreBoard().get(l).getOperation() != null) {
				if (commitWriteScoreBoard.getScoreBoard().get(l).getOperation() == null)
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (originalScoreBoard.getScoreBoard().get(l).getOperation() == null) {
					if (finalscoreboard.getScoreBoard().get(l).getOperation() != null) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// add reservation stations
		for (int i = 1; i <= originalScoreBoard.getAdd(); i++) {
			String l = "add" + i;
			if (originalScoreBoard.getScoreBoard().get(l).getOperation() != null) {
				if (commitWriteScoreBoard.getScoreBoard().get(l).getOperation() == null)
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (originalScoreBoard.getScoreBoard().get(l).getOperation() == null) {
					if (finalscoreboard.getScoreBoard().get(l).getOperation() != null) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// mult reservation stations
		for (int i = 1; i <= originalScoreBoard.getMult(); i++) {
			String l = "mult" + i;
			if (originalScoreBoard.getScoreBoard().get(l).getOperation() != null) {
				if (commitWriteScoreBoard.getScoreBoard().get(l).getOperation() == null)
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (originalScoreBoard.getScoreBoard().get(l).getOperation() == null) {
					if (finalscoreboard.getScoreBoard().get(l).getOperation() != null) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// jump reservation stations
		for (int i = 1; i <= originalScoreBoard.getJump(); i++) {
			String l = "jump" + i;
			if (originalScoreBoard.getScoreBoard().get(l).getOperation() != null) {
				if (commitWriteScoreBoard.getScoreBoard().get(l).getOperation() == null)
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (originalScoreBoard.getScoreBoard().get(l).getOperation() == null) {
					if (finalscoreboard.getScoreBoard().get(l).getOperation() != null) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		// return reservation stations
		for (int i = 1; i <= originalScoreBoard.getRet(); i++) {
			String l = "ret" + i;
			if (originalScoreBoard.getScoreBoard().get(l).getOperation() != null) {
				if (commitWriteScoreBoard.getScoreBoard().get(l).getOperation() == null)
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (originalScoreBoard.getScoreBoard().get(l).getOperation() == null) {
					if (finalscoreboard.getScoreBoard().get(l).getOperation() != null) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}
		// nand reservation stations
		for (int i = 1; i <= originalScoreBoard.getNand(); i++) {
			String l = "nand" + i;
			if (originalScoreBoard.getScoreBoard().get(l).getOperation() != null) {
				if (commitWriteScoreBoard.getScoreBoard().get(l).getOperation() == null)
					originalScoreBoard.getScoreBoard().put(
							l,
							new ScoreBoardEntry(false, null, "", "", null,
									null, null, 0));
			} else {
				if (originalScoreBoard.getScoreBoard().get(l).getOperation() == null) {
					if (finalscoreboard.getScoreBoard().get(l).getOperation() != null) {
						originalScoreBoard.getScoreBoard().put(l,
								finalscoreboard.getScoreBoard().get(l));
					}
				}
			}
		}

		return originalScoreBoard;
	}

}
