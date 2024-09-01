export default function getTierName(n: number) {
  const tier = [
    "없음",
    "브론즈",
    "실버",
    "골드",
    "플레티넘",
    "다이아몬드",
    "마스터",
  ];
  return tier[n];
}
