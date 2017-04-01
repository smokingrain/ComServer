package com.xk.server.cclogic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Position {
	
	
	static{
		init();
	}

	public static Random random = new Random();

	public int binarySearch(Integer[][] vlss, int vl) {
		int low = 0;
		int high = vlss.length - 1;
		while (low <= high) {
			int mid = (low + high) >> 1;
			if (vlss[mid][0] < vl) {
				low = mid + 1;
			} else if (vlss[mid][0] > vl) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	public static final int MAX_GEN_MOVES = 128;
	public static final int MATE_VALUE = 10000;
	public static final int BAN_VALUE = MATE_VALUE - 100;
	public static final int WIN_VALUE = MATE_VALUE - 200;
	public static final int NULL_SAFE_MARGIN = 400;
	public static final int NULL_OKAY_MARGIN = 200;
	public static final int DRAW_VALUE = 20;
	public static final int ADVANCED_VALUE = 3;

	public static final int PIECE_KING = 0;
	public static final int PIECE_ADVISOR = 1;
	public static final int PIECE_BISHOP = 2;
	public static final int PIECE_KNIGHT = 3;
	public static final int PIECE_ROOK = 4;
	public static final int PIECE_CANNON = 5;
	public static final int PIECE_PAWN = 6;

	public static final int RANK_TOP = 3;
	public static final int RANK_BOTTOM = 12;
	public static final int FILE_LEFT = 3;
	public static final int FILE_RIGHT = 11;

	public static final boolean ADD_PIECE = false;
	public static final boolean DEL_PIECE = true;

	public static final String[] STARTUP_FEN = {
			"rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR w - - 0 1",
			"rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/R1BAKABNR w - - 0 1",
			"rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/R1BAKAB1R w - - 0 1",
			"rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/9/1C5C1/9/RN2K2NR w - - 0 1", };

	public static final byte[] IN_BOARD = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
			0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
			0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, };

	public static final byte[] IN_FORT = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
			1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, };
	public static final byte[] LEGAL_SPAN = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };

	public static final byte[] KNIGHT_PIN = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -16, 0, -16, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0,
			1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 16, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public static final int[] KING_DELTA = { -16, -1, 1, 16 };
	public static final int[] ADVISOR_DELTA = { -17, -15, 15, 17 };
	public static final int[][] KNIGHT_DELTA = { { -33, -31 }, { -18, 14 },
			{ -14, 18 }, { 31, 33 } };
	public static final int[][] KNIGHT_CHECK_DELTA = { { -33, -18 },
			{ -31, -14 }, { 14, 31 }, { 18, 33 } };
	public static final int[] MVV_VALUE = { 50, 10, 10, 30, 40, 30, 20, 0 };

	public static final short[][] PIECE_VALUE = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9, 11, 13, 11, 9, 9, 9, 0,
					0, 0, 0, 0, 0, 0, 19, 24, 34, 42, 44, 42, 34, 24, 19, 0, 0,
					0, 0, 0, 0, 0, 19, 24, 32, 37, 37, 37, 32, 24, 19, 0, 0, 0,
					0, 0, 0, 0, 19, 23, 27, 29, 30, 29, 27, 23, 19, 0, 0, 0, 0,
					0, 0, 0, 14, 18, 20, 27, 29, 27, 20, 18, 14, 0, 0, 0, 0, 0,
					0, 0, 7, 0, 13, 0, 16, 0, 13, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7,
					0, 7, 0, 15, 0, 7, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
					1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 15, 11, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 20, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 18, 0, 0, 20, 23, 20, 0, 0, 18, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20,
					20, 0, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 20, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 18, 0, 0, 20, 23, 20, 0, 0, 18, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20,
					20, 0, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 90, 90, 96, 90, 96, 90, 90,
					90, 0, 0, 0, 0, 0, 0, 0, 90, 96, 103, 97, 94, 97, 103, 96,
					90, 0, 0, 0, 0, 0, 0, 0, 92, 98, 99, 103, 99, 103, 99, 98,
					92, 0, 0, 0, 0, 0, 0, 0, 93, 108, 100, 107, 100, 107, 100,
					108, 93, 0, 0, 0, 0, 0, 0, 0, 90, 100, 99, 103, 104, 103,
					99, 100, 90, 0, 0, 0, 0, 0, 0, 0, 90, 98, 101, 102, 103,
					102, 101, 98, 90, 0, 0, 0, 0, 0, 0, 0, 92, 94, 98, 95, 98,
					95, 98, 94, 92, 0, 0, 0, 0, 0, 0, 0, 93, 92, 94, 95, 92,
					95, 94, 92, 93, 0, 0, 0, 0, 0, 0, 0, 85, 90, 92, 93, 78,
					93, 92, 90, 85, 0, 0, 0, 0, 0, 0, 0, 88, 85, 90, 88, 90,
					88, 90, 85, 88, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 206, 208, 207, 213, 214, 213,
					207, 208, 206, 0, 0, 0, 0, 0, 0, 0, 206, 212, 209, 216,
					233, 216, 209, 212, 206, 0, 0, 0, 0, 0, 0, 0, 206, 208,
					207, 214, 216, 214, 207, 208, 206, 0, 0, 0, 0, 0, 0, 0,
					206, 213, 213, 216, 216, 216, 213, 213, 206, 0, 0, 0, 0, 0,
					0, 0, 208, 211, 211, 214, 215, 214, 211, 211, 208, 0, 0, 0,
					0, 0, 0, 0, 208, 212, 212, 214, 215, 214, 212, 212, 208, 0,
					0, 0, 0, 0, 0, 0, 204, 209, 204, 212, 214, 212, 204, 209,
					204, 0, 0, 0, 0, 0, 0, 0, 198, 208, 204, 212, 212, 212,
					204, 208, 198, 0, 0, 0, 0, 0, 0, 0, 200, 208, 206, 212,
					200, 212, 206, 208, 200, 0, 0, 0, 0, 0, 0, 0, 194, 206,
					204, 212, 200, 212, 204, 206, 194, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 100, 100, 96, 91, 90, 91, 96,
					100, 100, 0, 0, 0, 0, 0, 0, 0, 98, 98, 96, 92, 89, 92, 96,
					98, 98, 0, 0, 0, 0, 0, 0, 0, 97, 97, 96, 91, 92, 91, 96,
					97, 97, 0, 0, 0, 0, 0, 0, 0, 96, 99, 99, 98, 100, 98, 99,
					99, 96, 0, 0, 0, 0, 0, 0, 0, 96, 96, 96, 96, 100, 96, 96,
					96, 96, 0, 0, 0, 0, 0, 0, 0, 95, 96, 99, 96, 100, 96, 99,
					96, 95, 0, 0, 0, 0, 0, 0, 0, 96, 96, 96, 96, 96, 96, 96,
					96, 96, 0, 0, 0, 0, 0, 0, 0, 97, 96, 100, 99, 101, 99, 100,
					96, 97, 0, 0, 0, 0, 0, 0, 0, 96, 97, 98, 98, 98, 98, 98,
					97, 96, 0, 0, 0, 0, 0, 0, 0, 96, 96, 97, 99, 99, 99, 97,
					96, 96, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9, 11, 13, 11, 9, 9, 9, 0,
					0, 0, 0, 0, 0, 0, 19, 24, 34, 42, 44, 42, 34, 24, 19, 0, 0,
					0, 0, 0, 0, 0, 19, 24, 32, 37, 37, 37, 32, 24, 19, 0, 0, 0,
					0, 0, 0, 0, 19, 23, 27, 29, 30, 29, 27, 23, 19, 0, 0, 0, 0,
					0, 0, 0, 14, 18, 20, 27, 29, 27, 20, 18, 14, 0, 0, 0, 0, 0,
					0, 0, 7, 0, 13, 0, 16, 0, 13, 0, 7, 0, 0, 0, 0, 0, 0, 0, 7,
					0, 7, 0, 15, 0, 7, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
					1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 15, 11, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

	public static boolean IN_BOARD(int sq) {
		return IN_BOARD[sq] != 0;
	}

	public static boolean IN_FORT(int sq) {
		return IN_FORT[sq] != 0;
	}

	public static int RANK_Y(int sq) {
		return sq >> 4;
	}

	public static int FILE_X(int sq) {
		return sq & 15;
	}

	public static int COORD_XY(int x, int y) {
		return x + (y << 4);
	}

	public static int SQUARE_FLIP(int sq) {
		return 254 - sq;
	}

	public static int FILE_FLIP(int x) {
		return 14 - x;
	}

	public static int RANK_FLIP(int y) {
		return 15 - y;
	}

	public static int MIRROR_SQUARE(int sq) {
		return COORD_XY(FILE_FLIP(FILE_X(sq)), RANK_Y(sq));
	}

	public static int SQUARE_FORWARD(int sq, int sd) {
		return sq - 16 + (sd << 5);
	}

	public static boolean KING_SPAN(int sqSrc, int sqDst) {
		return LEGAL_SPAN[sqDst - sqSrc + 256] == 1;
	}

	public static boolean ADVISOR_SPAN(int sqSrc, int sqDst) {
		return LEGAL_SPAN[sqDst - sqSrc + 256] == 2;
	}

	public static boolean BISHOP_SPAN(int sqSrc, int sqDst) {
		return LEGAL_SPAN[sqDst - sqSrc + 256] == 3;
	}

	public static int BISHOP_PIN(int sqSrc, int sqDst) {
		return (sqSrc + sqDst) >> 1;
	}

	public static int KNIGHT_PIN(int sqSrc, int sqDst) {
		return sqSrc + KNIGHT_PIN[sqDst - sqSrc + 256];
	}

	public static boolean HOME_HALF(int sq, int sd) {
		return (sq & 0x80) != (sd << 7);
	}

	public static boolean AWAY_HALF(int sq, int sd) {
		return (sq & 0x80) == (sd << 7);
	}

	public static boolean SAME_HALF(int sqSrc, int sqDst) {
		return ((sqSrc ^ sqDst) & 0x80) == 0;
	}

	public static boolean SAME_RANK(int sqSrc, int sqDst) {
		return ((sqSrc ^ sqDst) & 0xf0) == 0;
	}

	public static boolean SAME_FILE(int sqSrc, int sqDst) {
		return ((sqSrc ^ sqDst) & 0x0f) == 0;
	}

	public static int SIDE_TAG(int sd) {
		return 8 + (sd << 3);
	}

	public static int OPP_SIDE_TAG(int sd) {
		return 16 - (sd << 3);
	}

	public static int SRC(int mv) {
		return mv & 255;
	}

	public static int DST(int mv) {
		return mv >> 8;
	}

	public static int MOVE(int sqSrc, int sqDst) {
		return sqSrc + (sqDst << 8);
	}

	public static int MIRROR_MOVE(int mv) {
		return MOVE(MIRROR_SQUARE(SRC(mv)), MIRROR_SQUARE(DST(mv)));
	}

	public static int MVV_LVA(int pc, int lva) {
		return MVV_VALUE[pc & 7] - lva;
	}

	public static final String FEN_PIECE = "        KABNRCP kabnrcp ";

	public static int CHAR_TO_PIECE(char c) {
		switch (c) {
		case 'K':
			return PIECE_KING;
		case 'A':
			return PIECE_ADVISOR;
		case 'B':
		case 'E':
			return PIECE_BISHOP;
		case 'H':
		case 'N':
			return PIECE_KNIGHT;
		case 'R':
			return PIECE_ROOK;
		case 'C':
			return PIECE_CANNON;
		case 'P':
			return PIECE_PAWN;
		default:
			return -1;
		}
	}

	public static int PreGen_zobristKeyPlayer;
	public static int PreGen_zobristLockPlayer;
	public static int[][] PreGen_zobristKeyTable = new int[14][256];
	public static int[][] PreGen_zobristLockTable = new int[14][256];

	static {
		Util.RC4 rc4 = new Util.RC4(new byte[] { 0 });
		PreGen_zobristKeyPlayer = rc4.nextLong();
		rc4.nextLong(); // Skip ZobristLock0
		PreGen_zobristLockPlayer = rc4.nextLong();
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 256; j++) {
				PreGen_zobristKeyTable[i][j] = rc4.nextLong();
				rc4.nextLong(); // Skip ZobristLock0
				PreGen_zobristLockTable[i][j] = rc4.nextLong();
			}
		}

	}

	public int sdPlayer;
	public byte[] squares = new byte[256];
	public int zobristKey;
	public int zobristLock;
	public int vlWhite;
	public int vlBlack;
	public int distance;

	public static final int MAX_MOVE_NUM = 256;

	public List<Integer> mvList = new ArrayList<Integer>();
	public List<Byte> pcList = new ArrayList<Byte>();
	public List<Integer> keyList = new ArrayList<Integer>();
	public List<Boolean> chkList = new ArrayList<Boolean>();

	public void clearBoard() {
		this.sdPlayer = 0;
		for (int sq = 0; sq < 256; sq++) {
			squares[sq] = 0;
		}
		this.zobristKey = this.zobristLock = 0;
		this.vlWhite = this.vlBlack = 0;
	};

	public void setIrrev() {
		this.mvList = new ArrayList<Integer>();
		mvList.add(0);
		this.pcList = new ArrayList<Byte>();
		pcList.add((byte) 0);
		this.keyList = new ArrayList<Integer>();
		keyList.add(0);
		chkList = new ArrayList<Boolean>();
		chkList.add(0, checked());
		this.distance = 0;
	}

	public void addPiece(int sq, int pc, boolean bDel) {
		int pcAdjust;
		squares[sq] = (byte) (bDel ? 0 : pc);
		if (pc < 16) {
			pcAdjust = pc - 8;
			this.vlWhite += bDel ? -PIECE_VALUE[pcAdjust][sq]
					: PIECE_VALUE[pcAdjust][sq];
		} else {
			pcAdjust = pc - 16;
			this.vlBlack += bDel ? -PIECE_VALUE[pcAdjust][SQUARE_FLIP(sq)]
					: PIECE_VALUE[pcAdjust][SQUARE_FLIP(sq)];
			pcAdjust += 7;
		}
		this.zobristKey ^= PreGen_zobristKeyTable[pcAdjust][sq];
		this.zobristLock ^= PreGen_zobristLockTable[pcAdjust][sq];
	}

	public void movePiece(int mv) {
		int sqSrc = SRC(mv);
		int sqDst = DST(mv);
		int pc = this.squares[sqDst];
		pcList.add(squares[sqDst]);
		if (pc > 0) {
			this.addPiece(sqDst, pc, DEL_PIECE);
		}
		pc = this.squares[sqSrc];
		this.addPiece(sqSrc, pc, DEL_PIECE);
		this.addPiece(sqDst, pc, ADD_PIECE);
		mvList.add(mv);
	}

	public void undoMovePiece() {
		int mv = mvList.remove(mvList.size() - 1);
		int sqSrc = SRC(mv);
		int sqDst = DST(mv);
		int pc = this.squares[sqDst];
		this.addPiece(sqDst, pc, DEL_PIECE);
		this.addPiece(sqSrc, pc, ADD_PIECE);
		pc = this.pcList.remove(pcList.size() - 1);
		if (pc > 0) {
			this.addPiece(sqDst, pc, ADD_PIECE);
		}
	}

	public void changeSide() {
		this.sdPlayer = 1 - this.sdPlayer;
		this.zobristKey ^= PreGen_zobristKeyPlayer;
		this.zobristLock ^= PreGen_zobristLockPlayer;
	}

	public boolean makeMove(int mv) {
		int zobristKey = this.zobristKey;
		this.movePiece(mv);
		if (this.checked()) {
			this.undoMovePiece();
			return false;
		}
		this.keyList.add(zobristKey);
		this.changeSide();
		this.chkList.add(this.checked());
		this.distance++;
		return true;
	}

	public void undoMakeMove() {
		this.distance--;
		this.chkList.remove(chkList.size() - 1);
		this.changeSide();
		this.keyList.remove(keyList.size() - 1);
		this.undoMovePiece();
	}

	public void nullMove() {
		this.mvList.add(0);
		this.pcList.add((byte) 0);
		this.keyList.add(this.zobristKey);
		this.changeSide();
		this.chkList.add(false);
		this.distance++;
	}

	public void undoNullMove() {
		this.distance--;
		this.chkList.remove(chkList.size() - 1);
		this.changeSide();
		this.keyList.remove(keyList.size() - 1);
		this.pcList.remove(pcList.size() - 1);
		this.mvList.remove(mvList.size() - 1);
	}

	public void fromFen(String fen) {
		this.clearBoard();
		int y = RANK_TOP;
		int x = FILE_LEFT;
		int index = 0;
		if (index == fen.length()) {
			this.setIrrev();
			return;
		}
		char c = fen.charAt(index);
		while (c != ' ') {
			if (c == '/') {
				x = FILE_LEFT;
				y++;
				if (y > RANK_BOTTOM) {
					break;
				}
			} else if (c >= '1' && c <= '9') {
				for (int k = 0; k < (c - '0'); k++) {
					if (x >= FILE_RIGHT) {
						break;
					}
					x++;
				}
			} else if (c >= 'A' && c <= 'Z') {
				if (x <= FILE_RIGHT) {
					int pt = CHAR_TO_PIECE(c);
					if (pt >= 0) {
						this.addPiece(COORD_XY(x, y), pt + 8, false);
					}
					x++;
				}
			} else if (c >= 'a' && c <= 'z') {
				if (x <= FILE_RIGHT) {
					int pt = CHAR_TO_PIECE((char) (c + 'A' - 'a'));
					if (pt >= 0) {
						addPiece(COORD_XY(x, y), pt + 16, false);
					}
					x++;
				}
			}
			index++;
			if (index == fen.length()) {
				this.setIrrev();
				return;
			}
			c = fen.charAt(index);
		}
		index++;
		if (index == fen.length()) {
			this.setIrrev();
			return;
		}
		if (this.sdPlayer == (fen.charAt(index) == 'b' ? 0 : 1)) {
			this.changeSide();
		}
		this.setIrrev();
	}

	public String toFen() {
		StringBuffer fen = new StringBuffer();
		for (int y = RANK_TOP; y <= RANK_BOTTOM; y++) {
			int k = 0;
			for (int x = FILE_LEFT; x <= FILE_RIGHT; x++) {
				int pc = squares[COORD_XY(x, y)];
				if (pc > 0) {
					if (k > 0) {
						fen.append((char) ('0' + k));
						k = 0;
					}
					fen.append(FEN_PIECE.charAt(pc));
				} else {
					k++;
				}
			}
			if (k > 0) {
				fen.append((char) ('0' + k));
			}
			fen.append('/');
		}
		fen.setCharAt(fen.length() - 1, ' ');
		fen.append(sdPlayer == 0 ? 'w' : 'b');
		return fen.toString();
	}

	public Integer[] generateAllMoves() {
		return generateMoves(null);
	}

	public Integer[] generateMoves(Integer[] vls_base) {
		List<Integer> vls = null;
		if (null != vls_base) {
			vls=new ArrayList<Integer>();
		}
		List<Integer> mvs = new ArrayList<Integer>();
		int pcSelfSide = SIDE_TAG(this.sdPlayer);
		int pcOppSide = OPP_SIDE_TAG(this.sdPlayer);
		for (int sqSrc = 0; sqSrc < 256; sqSrc++) {
			int pcSrc = this.squares[sqSrc];
			if ((pcSrc & pcSelfSide) == 0) {
				continue;
			}
			switch (pcSrc - pcSelfSide) {
			case PIECE_KING:
				for (int i = 0; i < 4; i++) {
					int sqDst = sqSrc + KING_DELTA[i];
					if (!IN_FORT(sqDst)) {
						continue;
					}
					int pcDst = this.squares[sqDst];
					if (vls == null) {
						if ((pcDst & pcSelfSide) == 0) {
							mvs.add(MOVE(sqSrc, sqDst));
						}
					} else if ((pcDst & pcOppSide) != 0) {
						mvs.add(MOVE(sqSrc, sqDst));
						vls.add(MVV_LVA(pcDst, 5));
					}
				}
				break;
			case PIECE_ADVISOR:
				for (int i = 0; i < 4; i++) {
					int sqDst = sqSrc + ADVISOR_DELTA[i];
					if (!IN_FORT(sqDst)) {
						continue;
					}
					int pcDst = this.squares[sqDst];
					if (vls == null) {
						if ((pcDst & pcSelfSide) == 0) {
							mvs.add(MOVE(sqSrc, sqDst));
						}
					} else if ((pcDst & pcOppSide) != 0) {
						mvs.add(MOVE(sqSrc, sqDst));
						vls.add(MVV_LVA(pcDst, 1));
					}
				}
				break;
			case PIECE_BISHOP:
				for (int i = 0; i < 4; i++) {
					int sqDst = sqSrc + ADVISOR_DELTA[i];
					if (!(IN_BOARD(sqDst) && HOME_HALF(sqDst, this.sdPlayer) && this.squares[sqDst] == 0)) {
						continue;
					}
					sqDst += ADVISOR_DELTA[i];
					int pcDst = this.squares[sqDst];
					if (vls == null) {
						if ((pcDst & pcSelfSide) == 0) {
							mvs.add(MOVE(sqSrc, sqDst));
						}
					} else if ((pcDst & pcOppSide) != 0) {
						mvs.add(MOVE(sqSrc, sqDst));
						vls.add(MVV_LVA(pcDst, 1));
					}
				}
				break;
			case PIECE_KNIGHT:
				for (int i = 0; i < 4; i++) {
					int sqDst = sqSrc + KING_DELTA[i];
					if (this.squares[sqDst] > 0) {
						continue;
					}
					for (int j = 0; j < 2; j++) {
						sqDst = sqSrc + KNIGHT_DELTA[i][j];
						if (!IN_BOARD(sqDst)) {
							continue;
						}
						int pcDst = this.squares[sqDst];
						if (vls == null) {
							if ((pcDst & pcSelfSide) == 0) {
								mvs.add(MOVE(sqSrc, sqDst));
							}
						} else if ((pcDst & pcOppSide) != 0) {
							mvs.add(MOVE(sqSrc, sqDst));
							vls.add(MVV_LVA(pcDst, 1));
						}
					}
				}
				break;
			case PIECE_ROOK:
				for (int i = 0; i < 4; i++) {
					int delta = KING_DELTA[i];
					int sqDst = sqSrc + delta;
					while (IN_BOARD(sqDst)) {
						int pcDst = this.squares[sqDst];
						if (pcDst == 0) {
							if (vls == null) {
								mvs.add(MOVE(sqSrc, sqDst));
							}
						} else {
							if ((pcDst & pcOppSide) != 0) {
								mvs.add(MOVE(sqSrc, sqDst));
								if (vls != null) {
									vls.add(MVV_LVA(pcDst, 4));
								}
							}
							break;
						}
						sqDst += delta;
					}
				}
				break;
			case PIECE_CANNON:
				for (int i = 0; i < 4; i++) {
					int delta = KING_DELTA[i];
					int sqDst = sqSrc + delta;
					while (IN_BOARD(sqDst)) {
						int pcDst = this.squares[sqDst];
						if (pcDst == 0) {
							if (vls == null) {
								mvs.add(MOVE(sqSrc, sqDst));
							}
						} else {
							break;
						}
						sqDst += delta;
					}
					sqDst += delta;
					while (IN_BOARD(sqDst)) {
						int pcDst = this.squares[sqDst];
						if (pcDst > 0) {
							if ((pcDst & pcOppSide) != 0) {
								mvs.add(MOVE(sqSrc, sqDst));
								if (vls != null) {
									vls.add(MVV_LVA(pcDst, 4));
								}
							}
							break;
						}
						sqDst += delta;
					}
				}
				break;
			case PIECE_PAWN:
				int sqDst = SQUARE_FORWARD(sqSrc, this.sdPlayer);
				if (IN_BOARD(sqDst)) {
					int pcDst = this.squares[sqDst];
					if (vls == null) {
						if ((pcDst & pcSelfSide) == 0) {
							mvs.add(MOVE(sqSrc, sqDst));
						}
					} else if ((pcDst & pcOppSide) != 0) {
						mvs.add(MOVE(sqSrc, sqDst));
						vls.add(MVV_LVA(pcDst, 2));
					}
				}
				if (AWAY_HALF(sqSrc, this.sdPlayer)) {
					for (int delta = -1; delta <= 1; delta += 2) {
						sqDst = sqSrc + delta;
						if (IN_BOARD(sqDst)) {
							int pcDst = this.squares[sqDst];
							if (vls == null) {
								if ((pcDst & pcSelfSide) == 0) {
									mvs.add(MOVE(sqSrc, sqDst));
								}
							} else if ((pcDst & pcOppSide) != 0) {
								mvs.add(MOVE(sqSrc, sqDst));
								vls.add(MVV_LVA(pcDst, 2));
							}
						}
					}
				}
				break;
			}
		}
		if (null != vls_base) {
			for (int i = 0; i < vls.size(); i++) {
				vls_base[i] = vls.get(i);
			}
		}
		return mvs.toArray(new Integer[] {});
	}

	public boolean legalMove(int mv) {
		int sqSrc = SRC(mv);
		int pcSrc = this.squares[sqSrc];
		int pcSelfSide = SIDE_TAG(this.sdPlayer);
		if ((pcSrc & pcSelfSide) == 0) {
			return false;
		}

		int sqDst = DST(mv);
		int pcDst = this.squares[sqDst];
		if ((pcDst & pcSelfSide) != 0) {
			return false;
		}
		int sqPin;
		switch (pcSrc - pcSelfSide) {
		case PIECE_KING:
			return IN_FORT(sqDst) && KING_SPAN(sqSrc, sqDst);
		case PIECE_ADVISOR:
			return IN_FORT(sqDst) && ADVISOR_SPAN(sqSrc, sqDst);
		case PIECE_BISHOP:
			return SAME_HALF(sqSrc, sqDst) && BISHOP_SPAN(sqSrc, sqDst)
					&& this.squares[BISHOP_PIN(sqSrc, sqDst)] == 0;
		case PIECE_KNIGHT:
			sqPin = KNIGHT_PIN(sqSrc, sqDst);
			return sqPin != sqSrc && this.squares[sqPin] == 0;
		case PIECE_ROOK:
		case PIECE_CANNON:
			int delta;
			if (SAME_RANK(sqSrc, sqDst)) {
				delta = (sqDst < sqSrc ? -1 : 1);
			} else if (SAME_FILE(sqSrc, sqDst)) {
				delta = (sqDst < sqSrc ? -16 : 16);
			} else {
				return false;
			}
			sqPin = sqSrc + delta;
			while (sqPin != sqDst && this.squares[sqPin] == 0) {
				sqPin += delta;
			}
			if (sqPin == sqDst) {
				return pcDst == 0 || pcSrc - pcSelfSide == PIECE_ROOK;
			}
			if (pcDst == 0 || pcSrc - pcSelfSide != PIECE_CANNON) {
				return false;
			}
			sqPin += delta;
			while (sqPin != sqDst && this.squares[sqPin] == 0) {
				sqPin += delta;
			}
			return sqPin == sqDst;
		case PIECE_PAWN:
			if (AWAY_HALF(sqDst, this.sdPlayer)
					&& (sqDst == sqSrc - 1 || sqDst == sqSrc + 1)) {
				return true;
			}
			return sqDst == SQUARE_FORWARD(sqSrc, this.sdPlayer);
		default:
			return false;
		}
	}

	public boolean checked() {
		int pcSelfSide = SIDE_TAG(this.sdPlayer);
		int pcOppSide = OPP_SIDE_TAG(this.sdPlayer);
		for (int sqSrc = 0; sqSrc < 256; sqSrc++) {
			if (this.squares[sqSrc] != pcSelfSide + PIECE_KING) {
				continue;
			}
			if (this.squares[SQUARE_FORWARD(sqSrc, this.sdPlayer)] == pcOppSide
					+ PIECE_PAWN) {
				return true;
			}
			for (int delta = -1; delta <= 1; delta += 2) {
				if (this.squares[sqSrc + delta] == pcOppSide + PIECE_PAWN) {
					return true;
				}
			}
			for (int i = 0; i < 4; i++) {
				if (this.squares[sqSrc + ADVISOR_DELTA[i]] != 0) {
					continue;
				}
				for (int j = 0; j < 2; j++) {
					int pcDst = this.squares[sqSrc + KNIGHT_CHECK_DELTA[i][j]];
					if (pcDst == pcOppSide + PIECE_KNIGHT) {
						return true;
					}
				}
			}
			for (int i = 0; i < 4; i++) {
				int delta = KING_DELTA[i];
				int sqDst = sqSrc + delta;
				while (IN_BOARD(sqDst)) {
					int pcDst = this.squares[sqDst];
					if (pcDst > 0) {
						if (pcDst == pcOppSide + PIECE_ROOK
								|| pcDst == pcOppSide + PIECE_KING) {
							return true;
						}
						break;
					}
					sqDst += delta;
				}
				sqDst += delta;
				while (IN_BOARD(sqDst)) {
					int pcDst = this.squares[sqDst];
					if (pcDst > 0) {
						if (pcDst == pcOppSide + PIECE_CANNON) {
							return true;
						}
						break;
					}
					sqDst += delta;
				}
			}
			return false;
		}
		return false;
	}

	public boolean isMate() {
		Integer[] mvs = this.generateMoves(null);
		for (int i = 0; i < mvs.length; i++) {
			int mv=mvs[i];
			if(mv==0){
				System.out.println();
			}
			if (this.makeMove(mv)) {
				this.undoMakeMove();
				return false;
			}
		}
		return true;
	}

	public int mateValue() {
		return this.distance - MATE_VALUE;
	}

	public int banValue() {
		return this.distance - BAN_VALUE;
	}

	public int drawValue() {
		return (this.distance & 1) == 0 ? -DRAW_VALUE : DRAW_VALUE;
	}

	public int evaluate() {
		int vl = (this.sdPlayer == 0 ? this.vlWhite - this.vlBlack
				: this.vlBlack - this.vlWhite) + ADVANCED_VALUE;
		return vl == this.drawValue() ? vl - 1 : vl;
	}

	public boolean nullOkay() {
		return (this.sdPlayer == 0 ? this.vlWhite : this.vlBlack) > NULL_OKAY_MARGIN;
	}

	public boolean nullSafe() {
		return (this.sdPlayer == 0 ? this.vlWhite : this.vlBlack) > NULL_SAFE_MARGIN;
	}

	public boolean inCheck() {
		return this.chkList.get(this.chkList.size() - 1);
	}

	public boolean captured() {
		return this.pcList.get(this.pcList.size() - 1) > 0;
	}

	public int repValue(int vlRep) {
		int vlReturn = ((vlRep & 2) == 0 ? 0 : this.banValue())
				+ ((vlRep & 4) == 0 ? 0 : -this.banValue());
		return vlReturn == 0 ? this.drawValue() : vlReturn;
	}

	public int repStatus(int recur_) {
		int recur = recur_;
		boolean selfSide = false;
		boolean perpCheck = true;
		boolean oppPerpCheck = true;
		int index = this.mvList.size()-1;
		while (this.mvList.get(index) > 0 && this.pcList.get(index) == 0) {
			if (selfSide) {
				perpCheck = perpCheck && this.chkList.get(index);
				if (this.keyList.get(index) == this.zobristKey) {
					recur--;
					if (recur == 0) {
						return 1 + (perpCheck ? 2 : 0) + (oppPerpCheck ? 4 : 0);
					}
				}
			} else {
				oppPerpCheck = oppPerpCheck && this.chkList.get(index);
			}
			selfSide = !selfSide;
			index--;
		}
		return 0;
	}

	public Position mirror() {
		Position pos = new Position();
		pos.clearBoard();
		for (int sq = 0; sq < 256; sq++) {
			int pc = this.squares[sq];
			if (pc > 0) {
				pos.addPiece(MIRROR_SQUARE(sq), pc, false);
			}
		}
		if (this.sdPlayer == 1) {
			pos.changeSide();
		}
		return pos;
	}

	public static Integer[][] BOOK_DAT;

	public static final int MAX_BOOK_SIZE = 16384;
	public static int bookSize = 0;
	public static int[] bookLock = new int[MAX_BOOK_SIZE];
	public static short[] bookMove = new short[MAX_BOOK_SIZE];
	public static short[] bookValue = new short[MAX_BOOK_SIZE];

	private static void init() {
		InputStream in = Position.class.getResourceAsStream("data/book/BOOK.DAT");
		if (in != null) {
			try {
				List<Integer[]> books = new ArrayList<Integer[]>();
				while (bookSize < MAX_BOOK_SIZE) {
					Integer[] values = new Integer[3];
					values[0] = Util.readInt(in) >>> 1;
					values[1] = Util.readShort(in);
					values[2] = Util.readShort(in);
					books.add(values);
					bookSize++;
				}
				BOOK_DAT = books.toArray(new Integer[][] {});
			} catch (Exception e) {
				// Exit "while" when IOException occurs
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (Exception e) {
				// Ignored
			}
		}
	}

	public int bookMove() {
		if (BOOK_DAT == null) {
			return 0;
		}
		boolean mirror = false;
		int lock = this.zobristLock >>> 1; // Convert into Unsigned
		int index = binarySearch(BOOK_DAT, lock);
		if (index < 0) {
			mirror = true;
			lock = this.mirror().zobristLock >>> 1; // Convert into Unsigned
			index = binarySearch(BOOK_DAT, lock);
		}
		if (index < 0) {
			return 0;
		}
		index--;
		while (index >= 0 && BOOK_DAT[index][0] == lock) {
			index--;
		}
		List<Integer> mvs = new ArrayList<Integer>();
		List<Integer> vls = new ArrayList<Integer>();
		int value = 0;
		index++;
		while (index < BOOK_DAT.length && BOOK_DAT[index][0] == lock) {
			int mv = BOOK_DAT[index][1];
			mv = (mirror ? MIRROR_MOVE(mv) : mv);
			if (this.legalMove(mv)) {
				mvs.add(mv);
				int vl = BOOK_DAT[index][2];
				vls.add(vl);
				value += vl;
			}
			index++;
		}
		if (value == 0) {
			return 0;
		}
		value = (int) Math.floor(Math.random() * value);
		for (index = 0; index < mvs.size(); index++) {
			value -= vls.get(index);
			if (value < 0) {
				break;
			}
		}
		return mvs.get(index);
	}

	public int historyIndex(int mv) {
		return ((this.squares[SRC(mv)] - 8) << 8) + DST(mv);
	}
}